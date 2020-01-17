package net.softbell.bsh.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.AuthStatusRule;
import net.softbell.bsh.domain.BanRule;
import net.softbell.bsh.domain.MemberRole;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberLoginLog;
import net.softbell.bsh.domain.repository.MemberLoginLogRepo;
import net.softbell.bsh.domain.repository.MemberRepo;
import net.softbell.bsh.dto.request.MemberDto;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class MemberService implements UserDetailsService
{
	// Global Field
	private final MemberRepo memberRepo;
	private final MemberLoginLogRepo memberLoginLogRepo;
	
	@Transactional
	public long joinUser(MemberDto memberDto)
	{
		// Log
		log.info(BellLog.getLogHead() + "회원가입 요청 (" + memberDto.getUserId() + " - " + memberDto.getUsername() + ")");
		
		// Field
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Member member;

		// Init
		member = getMember(memberDto.getUserId());
		
		// Exception
		if (member != null) // 같은 아이디로 회원가입이 되어있다면,
		{
			log.info(BellLog.getLogHead() + "member 객체: " + member.getUserId());
			return -1; // 돌아가세요~~
		}
		/*if (memberDto.getPassword().length() < 6 || memberDto.getPassword().length() > 20)
			return -1;
		if (memberDto.getUserId().length() < 4 || memberDto.getPassword().length() > 20)
			return -1;*/

		// Process
		memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword())); // 비밀번호 암호화

		log.info(BellLog.getLogHead() + "가입완료"); // TEST #### TODO
		return memberRepo.save(memberDto.toEntity()).getMemberId();
	}

	public Member getMember(String userId)
	{
		// Field
		Member member = memberRepo.findByUserId(userId);

		// Check
		if (member == null)
			return null;
		
		// Return
		return member;
	}
	
	public Member getMember(long id)
	{
		// Field
		Optional<Member> optMember = memberRepo.findById(id);

		// Check
		if (!optMember.isPresent())
			return null;
		return optMember.get();
	}
	
	public Member getAdminMember(String userId)
	{
		// Field
    	Member member;
    	
    	// Init
    	member = getMember(userId);
    	
    	// Exception
    	if (member == null)
    		return null;
    	
		// Auth Check
		if (!(member.getPermission() == MemberRole.ADMIN || member.getPermission() == MemberRole.SUPERADMIN) || 
				member.getBan() != BanRule.NORMAL) // 관리자가 아니거나 정지 회원이면 로그아웃처리
			return null;
		
		return member;
	}
	
	public Member loginMember(String id, String password)
	{
		// Field
		Member member;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Init
		member = getMember(id);

		// Exception
		if (member == null || !passwordEncoder.matches(password, member.getPassword())) // 현재 등록된 비번이 다르면
			return null;

		// Return
		return member;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException
	{
		// Field
		Member member;
		
		// Init
		member = getMember(userId);
		
		// Ban Check
		if (member != null)
			if (member.getBan() != BanRule.NORMAL)
				member.setPermission(MemberRole.BAN);
		
		// Return
		return member;
	}
	
	@Transactional
	public boolean procLogin(String userId, String loginIp, boolean isLogin)
	{
		// Field
		Member member;
		MemberLoginLog memberLoginLog;
		
		// Init
		member = getMember(userId);
		
		// Exception
		if (member == null)
			return false;
		
		// Process
		memberLoginLog = MemberLoginLog.builder()
									.member(member)
									.requestDate(new Date())
									.ipv4(loginIp)
									.build();
		if (isLogin)
			memberLoginLog.setStatus(AuthStatusRule.SUCCESS);
		else
			memberLoginLog.setStatus(AuthStatusRule.FAIL);

		// Process - DB Save
		memberLoginLogRepo.save(memberLoginLog);
		
		// Return
		return true;
	}

	/*@Transactional
	public boolean checkDelete(String userId) {
		// Field
		Member member;

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
		Member member;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Init
		member = getMember(memberDto.getUserId());

		// Exception
		if (member == null || !passwordEncoder.matches(memberDto.getPassword(), member.getPassword())) // 현재 등록된 비번이 다르면
			return false;

		// Process
		memberRepo.delete(member);
		
		return true;
	}*/
	
	@Transactional
	public boolean deleteUserList(Principal principal, List<Integer> listMemberId)
	{
		// Log
		log.info(BellLog.getLogHead() + "회원 탈퇴 요청 (" + listMemberId.size() + ") (" + principal.getName() + ")");
		
		// Field
		boolean isError = false;
		Member memberMySelf = getMember(principal.getName());

		// Init
		for (int value : listMemberId)
		{
			Member member = getMember(value);
	
			// Exception
			if (member == null || member == memberMySelf) // 해당하는 회원이 없으면
			{
				isError = true;
				continue;
			}
			if (member.getPermission() == MemberRole.SUPERADMIN  && memberMySelf.getPermission() != MemberRole.SUPERADMIN) // 최고관리자가 아니면 최고관리자 제어 불가
				continue;
	
			// Process
			memberRepo.delete(member);
		}
		
		return !isError;
	}
/*
	public MemberDTO getInfo(Principal principal) {
		// Field
		MemberDTO memberDTO;
		Member member;

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
*/
	@Transactional
	public Member modifyInfo(Principal principal, String strCurPassword, String strModPassword)
	{
		// Log
		log.info(BellLog.getLogHead() + "회원정보 수정 요청 (" + principal.getName() + ")");
		
		// Field
		Member member;
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Init
		member = getMember(principal.getName());

		// Exception
		if (member == null)
			return null;
		/*if (strModPassword.length() < 6 || strModPassword.length() > 20) // 비밀번호 규칙
			return null;*/
		if (!passwordEncoder.matches(strCurPassword, member.getPassword())) // 현재 등록된 비번이 다르면
			return null;

		// Process
		member.setPassword(passwordEncoder.encode(strModPassword)); // 비밀번호 암호화

		// Process - DB Update
		member = memberRepo.save(member);

		// Return
		return member;
	}

	public Page<Member> getMemberList(int intPage, int intCount)
	{
		// Field
		Page<Member> pageMember;
		Pageable curPage;

		// Init
		curPage = PageRequest.of(intPage - 1, intCount, new Sort(Direction.DESC, "memberId"));
		pageMember = memberRepo.findAll(curPage);

		// Return
		return pageMember;
	}
/*
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
	}*/
	
	public boolean procMemberApproval(Principal principal, List<Integer> listMemberId, boolean isApproval, boolean isMember)
	{
		// Log
		log.info(BellLog.getLogHead() + listMemberId.size() + "명 회원 승인(" + isApproval + ") 처리 (" + principal.getName() + ")");
		
		// Field
		int intSuccess = listMemberId.size();
		Member memberMySelf;

		// Init
		memberMySelf = getAdminMember(principal.getName());
		
		// Exception
		if (memberMySelf == null)
			return false;
		
		// Process
		for (int intMemberId : listMemberId) {
			// Field
			Member member;

			// Init
			member = getMember(intMemberId);
			
			// Exception
			if (member == null)
			{
				intSuccess--;
				continue;
			}
			if (member.getPermission() != MemberRole.WAIT) // 승인 대기중 회원만 제어 가능
				continue;

			// Process
			if (isApproval)
				if (isMember)
					member.setPermission(MemberRole.MEMBER);
				else
					member.setPermission(MemberRole.NODE);
			else
				member.setPermission(MemberRole.BAN);

			// Process - DB Update
			memberRepo.save(member);
		}

		// Return
		if (intSuccess <= 0)
			return false;
		return true;
	}

	public boolean procMemberBan(Principal principal, List<Integer> listMemberId, boolean isBan)
	{
		// Log
		log.info(BellLog.getLogHead() + listMemberId.size() + "명 회원 정지(" + isBan + ") 처리 (" + principal.getName() + ")");
		
		// Field
		int intSuccess = listMemberId.size();
		Member memberMySelf;

		// Init
		memberMySelf = getMember(principal.getName());
		
		// Exception
		if (memberMySelf == null)
			return false;
		
		// Process
		for (int intMemberId : listMemberId)
		{
			// Field
			Member member;

			// Init
			member = getMember(intMemberId);
			
			// Exception
			if (member == null || member == memberMySelf)
			{
				intSuccess--;
				continue;
			}
			if (member.getPermission() == MemberRole.SUPERADMIN  && memberMySelf.getPermission() != MemberRole.SUPERADMIN) // 최고관리자가 아니면 최고관리자 제어 불가
				continue;

			// Process
			if (isBan)
				member.setBan(BanRule.PERMANENT);
			else
				member.setBan(BanRule.NORMAL);

			// Process - DB Update
			memberRepo.save(member);
		}

		// Return
		if (intSuccess <= 0)
			return false;
		return true;
	}

	public boolean procSetAdmin(Principal principal, List<Integer> listMemberId, boolean isAdd)
	{
		// Log
		log.info(BellLog.getLogHead() + listMemberId.size() + "명 관리자 설정(" + isAdd + ") 처리 (" + principal.getName() + ")");
		
		// Exception
		
		
		// Field
		int intSuccess = listMemberId.size();
		Member memberMySelf = getMember(principal.getName());
		
		// Exception
		if (memberMySelf.getPermission() != MemberRole.SUPERADMIN) // 권한 제어는 최고 관리자가 아니면 불가능
			return false;

		// Process
		for (int intMemberId : listMemberId)
		{
			// Field
			Member member;

			// Init
			member = getMember(intMemberId);
			
			// Exception
			if (member == null || member == memberMySelf)
			{
				intSuccess--;
				continue;
			}

			// Process
			if (isAdd)
			{
				if (member.getPermission() == MemberRole.MEMBER)
					member.setPermission(MemberRole.ADMIN);
			}
			else
				if (member.getPermission() == MemberRole.ADMIN)
					member.setPermission(MemberRole.MEMBER);

			// Process - DB Update
			memberRepo.save(member);
		}

		// Return
		if (intSuccess <= 0)
			return false;
		return true;
	}
	
	public Page<MemberLoginLog> getLoginLog(Principal principal, int intPage, int intCount)
	{
		// Field
		Member member;
		Page<MemberLoginLog> pageMemberLoginLog;
		Pageable curPage;
		
		// Init
		member = getMember(principal.getName());
		
		// Exception
		if (member == null)
			return null;
		
		// Init
		curPage = PageRequest.of(intPage - 1, intCount, new Sort(Direction.DESC, "logId"));
		pageMemberLoginLog = memberLoginLogRepo.findByMember(member, curPage);
		
		// Process
		
		// Return
		return pageMemberLoginLog;
	}
	
	public long getLoginLogMaxPage(Principal principal, int intCount)
	{
		// Field
		Member member;
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
	}
}
