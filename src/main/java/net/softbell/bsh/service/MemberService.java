package net.softbell.bsh.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.MemberRole;
import net.softbell.bsh.domain.entity.MemberInfo;
import net.softbell.bsh.domain.repository.MemberInfoRepo;
import net.softbell.bsh.domain.repository.MemberLoginRepo;
import net.softbell.bsh.dto.member.MemberInfoDTO;
import net.softbell.bsh.libs.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 서비스
 */
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberInfoRepo memberRepo;
	@Autowired
	private MemberLoginRepo memberLoginLogRepo;

	@Transactional
	public int joinUser(MemberInfoDTO memberDto) {
		// Log
		G_Logger.info(BellLog.getLogHead() + "회원가입 요청 (" + memberDto.getUserId() + " - " + memberDto.getUsername() + ")");
		
		// Field
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		MemberInfo member;

		// Init
		member = getMember(memberDto.getUserId());

		// Exception
		if (member != null) // 같은 아이디로 회원가입이 되어있다면,
			return -1; // 돌아가세요~~
		/*if (memberDto.getPassword().length() < 6 || memberDto.getPassword().length() > 20)
			return -1;
		if (memberDto.getUserId().length() < 4 || memberDto.getPassword().length() > 20)
			return -1;*/

		// Process
		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword())); // 비밀번호 암호화

		return memberRepo.save(memberDto.toEntity()).getMemberId();
	}

	public MemberInfo getMember(String userId)
	{
		// Field
		Optional<MemberInfo> optMember = memberRepo.findByUserId(userId);

		// Check
		if (!optMember.isPresent())
			return null;
		return optMember.get();
	}
	
	public MemberInfo getMember(long id)
	{
		// Field
		Optional<MemberInfo> optMember = memberRepo.findById(id);

		// Check
		if (!optMember.isPresent())
			return null;
		return optMember.get();
	}
	
	/*public boolean isAdmin(String userId)
	{
		MemberInfo member = getMember(userId);
		
		if (member != null && member.getIsAdmin().equalsIgnoreCase("Y") || member.getIsAdmin().equalsIgnoreCase("1"))
			return true;
		return false;
	}
	
	public boolean isSuperAdmin(String userId)
	{
		MemberInfo member = getMember(userId);
		
		if (member != null && member.getIsAdmin().equalsIgnoreCase("1"))
			return true;
		return false;
	}
	
	public boolean isBan(String userId)
	{
		MemberInfo member = getMember(userId);
		
		if (member == null || member.getIsBan().equalsIgnoreCase("Y"))
			return true;
		return false;
	}*/

//	public User readUser(String username)
//	{
//		User user = new User()
//	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		// Field
		MemberInfo member;
		
		// Init
		member = getMember(userId);
		if (member == null)
			return null;

		List<GrantedAuthority> authorities = new ArrayList<>();

		if (member.getIsBan() == 1)
			authorities.add(new SimpleGrantedAuthority(MemberRole.BAN.getValue()));
		if (member.getIsAdmin() == 1)
			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		if (member.getIsAdmin() == 2)
		{
			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
			authorities.add(new SimpleGrantedAuthority(MemberRole.SUPERADMIN.getValue()));
		}
		authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));

		return new User(member.getUserId(), member.getPassword(), authorities);
	}
	
	/*@Transactional
	public boolean procLogin(String userId, String loginIp, boolean isLogin)
	{
		// Field
		MemberInfo member;
		MemberLoginLog memberLoginLog;
		
		// Init
		member = getMember(userId);
		
		// Exception
		if (member == null)
			return false;
		
		// Process
		memberLoginLog = MemberLoginLog.builder()
									.member(member)
									.loginDate(new Date())
									.loginIp(loginIp)
									.build();
		if (isLogin)
			memberLoginLog.setIsLogin("Y");
		else
			memberLoginLog.setIsLogin("N");

		// Process - DB Save
		memberLoginLogRepo.save(memberLoginLog);
		
		// Return
		return true;
	}

	@Transactional
	public boolean checkDelete(String userId) {
		// Field
		MemberInfo member;

		// Init
		member = getMember(userId);

		// Check
		if (member == null || !member.getProducts().isEmpty() || !member.getMemberOrders().isEmpty())
			return false;

		// Return
		return true;
	}
	
	@Transactional
	public boolean deleteUser(MemberDTO memberDto) {
		// Log
		G_Logger.info(BellLog.getLogHead() + "회원 탈퇴 요청 (" + memberDto.getUserId() + ")");
		
		// Field
		MemberInfo member;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Init
		member = getMember(memberDto.getUserId());

		// Exception
		if (member == null || !passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) // 현재 등록된 비번이 다르면
			return false;

		// Process
		memberRepo.delete(member);
		
		return true;
	}
	
	@Transactional
	public boolean deleteUserList(Principal principal, List<Integer> listMemberId) {
		// Log
		G_Logger.info(BellLog.getLogHead() + "회원 탈퇴 요청 (" + listMemberId.size() + ")");
		
		// Field
		MemberInfo member;
		boolean isError = false;
		boolean isSuperAdmin = isSuperAdmin(principal.getName());
		MemberInfo memberMySelf = getMember(principal.getName());

		// Init
		for (int value : listMemberId)
		{
			member = getMember(value);
	
			// Exception
			if (member == null || member == memberMySelf) // 해당하는 회원이 없으면
			{
				isError = true;
				continue;
			}
			if (member.getIsAdmin().equalsIgnoreCase("1") && !isSuperAdmin) // 최고관리자가 아니면 최고관리자 제어 불가
				continue;
	
			// Process
			memberRepo.delete(member);
		}
		
		return !isError;
	}

	public MemberDTO getInfo(Principal principal) {
		// Field
		MemberDTO memberDTO;
		MemberInfo member;

		// Init
		member = getMember(principal.getName());

		// Exception
		if (member == null)
			return null;

		// Process
		memberDTO = MemberDTO.builder().id(member.getMemberId()).userId(member.getUserId())
				.username(member.getUsername()).birthday(member.getBirthday()).regDate(member.getRegdate())
				.isBan(member.getIsBan()).isAdmin(member.getIsAdmin()).build();

		// return
		return memberDTO;
	}

	@Transactional
	public MemberInfo modifyInfo(Principal principal, String strCurPassword, String strModPassword) {
		// Log
		G_Logger.info(BellLog.getLogHead() + "회원정보 수정 요청 (" + principal.getName() + ")");
		
		// Field
		MemberInfo member;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Init
		member = getMember(principal.getName());

		// Exception
		if (member == null)
			return null;
		if (strModPassword.length() < 6 || strModPassword.length() > 20) // 비밀번호 규칙
			return null;
		if (!passwordEncoder.matches(strCurPassword, member.getPassword())) // 현재 등록된 비번이 다르면
			return null;

		// Process
		member.setPassword(passwordEncoder.encode(strModPassword)); // 비밀번호 암호화

		// Process - DB Update
		member = memberRepo.save(member);

		// Return
		return member;
	}

	public List<MemberDTO> getMemberList(int intPage, int intCount) {
		// Field
		Page<MemberInfo> pageMember;
		Pageable curPage;
		List<MemberDTO> listMemberDTO;

		// Init
		curPage = PageRequest.of(intPage - 1, intCount, new Sort(Direction.DESC, "memberId"));
		pageMember = memberRepo.findAll(curPage);
		listMemberDTO = new ArrayList<MemberDTO>();

		// Process
		for (MemberInfo member : pageMember) {
			listMemberDTO.add(MemberDTO.builder().id(member.getMemberId()).userId(member.getUserId())
					.username(member.getUsername()).birthday(member.getBirthday()).regDate(member.getRegdate())
					.isBan(member.getIsBan()).isAdmin(member.getIsAdmin()).build());
		}

		// Return
		return listMemberDTO;
	}

	public int getMemberMaxPage(int intCount) {
		// Field
		long longCount;
		int intMaxPage;

		// Process
		longCount = memberRepo.count();
		intMaxPage = (int) (longCount / intCount);
		if (longCount % intCount != 0)
			intMaxPage += 1;

		// Return
		return intMaxPage;
	}

	public boolean procMemberBan(Principal principal, List<Integer> listMemberId, boolean isBan) {
		// Log
		G_Logger.info(BellLog.getLogHead() + listMemberId.size() + "명 회원 정지(" + isBan + ") 처리 (" + principal.getName() + ")");
		
		// Field
		int intSuccess = listMemberId.size();
		boolean isSuperAdmin = isSuperAdmin(principal.getName());
		MemberInfo memberMySelf = getMember(principal.getName());

		// Process
		for (int intMemberId : listMemberId) {
			// Field
			MemberInfo member;

			// Init
			member = getMember(intMemberId);
			
			// Exception
			if (member == null || member == memberMySelf) {
				intSuccess--;
				continue;
			}
			if (member.getIsAdmin().equalsIgnoreCase("1") && !isSuperAdmin) // 최고관리자가 아니면 최고관리자 제어 불가
				continue;

			// Process
			if (isBan)
				member.setIsBan("Y");
			else
				member.setIsBan("N");

			// Process - DB Update
			memberRepo.save(member);
		}

		// Return
		if (intSuccess <= 0)
			return false;
		return true;
	}

	public boolean procSetAdmin(Principal principal, List<Integer> listMemberId, boolean isAdd) {
		// Log
		G_Logger.info(BellLog.getLogHead() + listMemberId.size() + "명 관리자 설정(" + isAdd + ") 처리 (" + principal.getName() + ")");
		
		// Exception
		if (!isSuperAdmin(principal.getName())) // 권한 제어는 최고 관리자가 아니면 불가능
			return false;
		
		// Field
		int intSuccess = listMemberId.size();
		MemberInfo memberMySelf = getMember(principal.getName());

		// Process
		for (int intMemberId : listMemberId) {
			// Field
			MemberInfo member;

			// Init
			member = getMember(intMemberId);
			
			// Exception
			if (member == null || member == memberMySelf) {
				intSuccess--;
				continue;
			}

			// Process
			if (isAdd)
				member.setIsAdmin("Y");
			else
				member.setIsAdmin("N");

			// Process - DB Update
			memberRepo.save(member);
		}

		// Return
		if (intSuccess <= 0)
			return false;
		return true;
	}
	
	public List<MemberLoginLogDTO> getLoginLog(Principal principal, int intPage, int intCount)
	{
		// Field
		MemberInfo member;
		List<MemberLoginLogDTO> listMemberLoginLog;
		Page<MemberLoginLog> pageMemberLoginLog;
		Pageable curPage;
		
		// Init
		member = getMember(principal.getName());
		
		// Exception
		if (member == null)
			return null;
		
		// Init
		listMemberLoginLog = new ArrayList<MemberLoginLogDTO>();
		curPage = PageRequest.of(intPage - 1, intCount, new Sort(Direction.DESC, "logId"));
		pageMemberLoginLog = memberLoginLogRepo.findByMember(member, curPage);
		
		// Process
		for (MemberLoginLog memberLoginLog : pageMemberLoginLog.getContent())
			listMemberLoginLog.add(new MemberLoginLogDTO(memberLoginLog));
		
		// Return
		return listMemberLoginLog;
	}
	
	public int getLoginLogMaxPage(Principal principal, int intCount) {
		// Field
		MemberInfo member;
		long longCount;
		int intMaxPage;
		
		// Init
		member = getMember(principal.getName());
		
		// Exception
		if (member == null)
			return 0;

		// Process
		longCount = memberLoginLogRepo.countByMember(member); // userid로 검색
		intMaxPage = (int) (longCount / intCount);
		if (longCount % intCount != 0)
			intMaxPage += 1;

		// Return
		return intMaxPage;
	}*/
}
