package net.softbell.bsh.service

import mu.KLogging
import net.softbell.bsh.domain.AuthStatusRule
import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberLoginLog
import net.softbell.bsh.domain.repository.*
import net.softbell.bsh.dto.request.MemberDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Principal
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 서비스
 */
@Service
class MemberService : UserDetailsService {
    // Global Field
    @Autowired private lateinit var centerService: CenterService
    @Autowired private lateinit var memberRepo: MemberRepo
    @Autowired private lateinit var  memberLoginLogRepo: MemberLoginLogRepo
    @Autowired private lateinit var memberInterlockTokenRepo: MemberInterlockTokenRepo
    @Autowired private lateinit var memberGroupItemRepo: MemberGroupItemRepo
    @Autowired private lateinit var nodeActionRepo: NodeActionRepo
    @Autowired private lateinit var nodeActionItemRepo: NodeActionItemRepo
    @Autowired private lateinit var nodeReservRepo: NodeReservRepo
    @Autowired private lateinit var nodeReservActionRepo: NodeReservActionRepo
    @Autowired private lateinit var nodeTriggerRepo: NodeTriggerRepo
    @Autowired private lateinit var nodeTriggerActionRepo: NodeTriggerActionRepo

    @Transactional
    fun joinUser(memberDto: MemberDto): Long {
        // Log
        logger.info("회원가입 요청 (" + memberDto.userId + " - " + memberDto.username + ")") // TODO

        // Field
        val passwordEncoder = BCryptPasswordEncoder()

        // Init
        val member: Member? = getMember(memberDto.userId)

        // Exception
        if (member != null) // 같은 아이디로 회원가입이 되어있다면,
        {
            logger.info("member 객체: " + member.userId)
            return -1 // 돌아가세요~~
        }
        /*if (memberDto.getPassword().length() < 6 || memberDto.getPassword().length() > 20)
			return -1;
		if (memberDto.getUserId().length() < 4 || memberDto.getPassword().length() > 20)
			return -1;*/

        // Process
        memberDto.password = passwordEncoder.encode(memberDto.password) // 비밀번호 암호화
        memberDto.ban = BanRule.NORMAL.code
        memberDto.permission = MemberRole.WAIT.code
        logger.info("가입완료") // TODO TEST ####
        return memberRepo.save(memberDto.toEntity()).memberId
    }

    fun getAllMember(): List<Member> {
        return memberRepo.findAll()
    }

    fun getMember(userId: String): Member? {
        return memberRepo.findByUserId(userId)
    }

    fun getMember(id: Long): Member? {
        // Field
        val optMember = memberRepo.findById(id)

        // Check
        return if (optMember.isPresent)
            optMember.get()
        else
            null
    }

    fun getAdminMember(userId: String): Member? {
        // Init
        val member: Member = getMember(userId) ?: return null

        // Auth Check
        return if (isAdmin(member))
            member
        else
            null
    }

    fun isAdmin(member: Member): Boolean {
        return (member.permission == MemberRole.ADMIN || member.permission == MemberRole.SUPERADMIN) &&
                member.ban == BanRule.NORMAL
    }

    fun loginMember(id: String, password: String): Member? {
        // Init
        val passwordEncoder = BCryptPasswordEncoder()
        val member: Member? = getMember(id)

        // Return
        return if (member != null && passwordEncoder.matches(password, member.password))
            member
        else
            null
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userId: String): UserDetails {
        // Init
        val member: Member = getMember(userId) ?: throw UsernameNotFoundException("저장된 회원 없음")

        // Login Fail Check
        if (isLoginCancel(member))
            throw UsernameNotFoundException("loginFail")

        // DB - Update
        member.lastLogin = Date()
        memberRepo.save(member)

        // Ban Check
        if (isLoginBan(member))
            member.permission = MemberRole.BAN

        // Return
        return member
    }

    @Throws(UsernameNotFoundException::class)
    fun tokenLoadUserByUsername(userId: String): UserDetails {
        // Init
        val member: Member = getMember(userId) ?: throw UsernameNotFoundException("저장된 회원 없음")

        // Ban Check
        if (isLoginBan(member))
            member.permission = MemberRole.BAN

        // Return
        return member
    }

    @Transactional
    fun procLogin(userId: String, loginIp: String, isLogin: Boolean): Boolean {
        // Init
        val memberLoginLog: MemberLoginLog
        val member: Member = getMember(userId) ?: return false

        // Process
        memberLoginLog = MemberLoginLog(
                member = member,
                requestDate = Date(),
                ipv4 = loginIp,
                status = AuthStatusRule.FAIL
        )

        if (isLogin) {
            memberLoginLog.status = AuthStatusRule.SUCCESS
            member.loginFailBanStart = null
        } else {
            // Init
            val fail: Long
            val start: Calendar = Calendar.getInstance()
            val end: Calendar = Calendar.getInstance()
            val failCheckTime: Int = centerService.setting.webLoginFailCheckTime
            val maxFailCount: Int = centerService.setting.webLoginFailMaxCount.toInt()

            start.add(Calendar.SECOND, -failCheckTime)
            fail = memberLoginLogRepo.countByMemberAndStatusAndRequestDateBetween(member, AuthStatusRule.FAIL, start.time, end.time) + 1

            // Check
            if (fail > maxFailCount) {
                member.loginFailBanStart = Date()
                logger.info(failCheckTime.toString() + "초 내 로그인 " + fail + "회 실패로 임시 차단 (" + maxFailCount + "회 제한) : " + member.userId)
            }
        }

        // Process - DB Save
        memberLoginLogRepo.save(memberLoginLog)

        // Return
        return true
    }

    fun isLoginBan(member: Member): Boolean {
        // 1st Check
        if (member.ban == BanRule.PERMANENT)
            return true

        // Init
        val now = Date()
        val ban = member.banDate ?: return false

        // Check
        if (member.ban == BanRule.TEMP)
            return now <= ban // Check

        // Default Return
        return false
    }

    fun isLoginCancel(member: Member): Boolean {
        // Init
        val ban = Calendar.getInstance()

        // Load
        ban.time = member.loginFailBanStart ?: return false // 차단되지 않았으면 로그인 실패 시각 로드
        ban.add(Calendar.SECOND, centerService.setting.webLoginFailBanTime) // 차단 시각 추가

        // Check
        return ban.time > Date() // 로그인 취소
    }

    /**
     * 한 회원에 대한 연관 데이터 전부 삭제
     * @param member
     * @return 성공 여부
     * 회원 탈퇴시 삭제해야될 데이터
     * member_interlock_token
     * member_login_log
     * member_group_item
     * node_action
     * node_action_item
     * node_reserv
     * node_reserv_action
     * node_trigger
     * node_trigger_item
     */
    @Transactional
    fun deleteMember(member: Member?): Boolean {
        // Exception
        return member != null

        // Field


        // Init


        // Process


        // Return
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
		G_Logger.info("회원 탈퇴 요청 (" + memberDto.getUserId() + ")");

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
		G_Logger.info("회원 탈퇴 요청 (" + memberDto.getUserId() + ")");

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
    fun deleteUser(member: Member): Boolean {
        // Init
        val listNodeAction = member.nodeActions

        // DB - Node Action Child Delete
        nodeReservActionRepo.deleteAllByNodeAction(listNodeAction)
        nodeTriggerActionRepo.deleteAllByNodeAction(listNodeAction)
        nodeActionItemRepo.deleteAllByNodeAction(listNodeAction)

        // DB - Node Delete
        nodeReservRepo.deleteByMember(member)
        nodeTriggerRepo.deleteByMember(member)
        nodeActionRepo.deleteByMember(member)

        // DB - Member Delete
        memberInterlockTokenRepo.deleteByMember(member)
        memberLoginLogRepo.deleteByMember(member)
        memberGroupItemRepo.deleteByMember(member)

        // DB - Post Delete
        memberRepo.delete(member)

        // Return
        return true
    }

    @Transactional
    fun deleteUserList(principal: Principal, listMemberId: List<Long>): Boolean {
        // Log
        logger.info("회원 탈퇴 요청 (" + listMemberId.size + ") (" + principal.name + ")")

        // Field
        var isError = false
        val memberMySelf = getMember(principal.name) ?: return false // 본인이 없으면 실패

        // Init
        for (value in listMemberId) {
            val member = getMember(value)

            // Exception
            if (member == null || member == memberMySelf) // 해당하는 회원이 없으면
            {
                isError = true
                continue
            }
            if (member.permission == MemberRole.SUPERADMIN && memberMySelf.permission != MemberRole.SUPERADMIN) // 최고관리자가 아니면 최고관리자 제어 불가
                continue

            // Process
            deleteUser(member)
        }
        return !isError
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
    fun modifyInfo(principal: Principal, strCurPassword: String, strModPassword: String): Member? {
        // Log
        logger.info("회원정보 수정 요청 (" + principal.name + ")")

        // Init
        val passwordEncoder = BCryptPasswordEncoder()
        var member: Member = getMember(principal.name) ?: return null

        // Exception
        /*if (strModPassword.length() < 6 || strModPassword.length() > 20) // 비밀번호 규칙
			return null;*/
        if (!passwordEncoder.matches(strCurPassword, member.password)) // 현재 등록된 비번이 다르면
            return null

        // Process
        member.password = passwordEncoder.encode(strModPassword) // 비밀번호 암호화

        // Process - DB Update
        member = memberRepo.save(member)

        // Return
        return member
    }

    fun getMemberList(intPage: Int, intCount: Int): Page<Member> {
        // Init
        val curPage = PageRequest.of(intPage - 1, intCount, Sort.by(Sort.Direction.DESC, "memberId"))

        // Return
        return memberRepo.findAll(curPage)
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
    fun procMemberApproval(principal: Principal, listMemberId: List<Long>, isApproval: Boolean, isMember: Boolean): Boolean {
        // Log
        logger.info(listMemberId.size.toString() + "명 회원 승인(" + isApproval + ") 처리 (" + principal.name + ")")

        // Init
        var intSuccess = listMemberId.size
        getAdminMember(principal.name) ?: return false // 본인이 관리자가 아니면 실패

        // Process
        for (memberId in listMemberId) {
            // Init
            val member = getMember(memberId)

            // Exception
            if (member == null) {
                intSuccess--
                continue
            }
            if (member.permission != MemberRole.WAIT) // 승인 대기중 회원만 제어 가능
                continue

            // Process
            if (isApproval)
                if (isMember)
                    member.permission = MemberRole.MEMBER
                else
                    member.permission = MemberRole.NODE
            else
                member.permission = MemberRole.BAN

            // Process - DB Update
            memberRepo.save(member)
        }

        // Return
        return intSuccess > 0
    }

    fun procMemberBan(principal: Principal, listMemberId: List<Long>, isBan: Boolean): Boolean {
        // Log
        logger.info(listMemberId.size.toString() + "명 회원 정지(" + isBan + ") 처리 (" + principal.name + ")")

        // Init
        var intSuccess = listMemberId.size
        val memberMySelf = getMember(principal.name) ?: return false

        // Process
        for (memberId in listMemberId) {
            // Init
            val member = getMember(memberId)

            // Exception
            if (member == null || member == memberMySelf) {
                intSuccess--
                continue
            }
            if (member.permission == MemberRole.SUPERADMIN && memberMySelf.permission != MemberRole.SUPERADMIN) // 최고관리자가 아니면 최고관리자 제어 불가
                continue

            // Process
            if (isBan)
                member.ban = BanRule.PERMANENT
            else
                member.ban = BanRule.NORMAL

            // Process - DB Update
            memberRepo.save(member)
        }

        // Return
        return intSuccess > 0
    }

    fun procSetAdmin(principal: Principal, listMemberId: List<Long>, isAdd: Boolean): Boolean {
        // Log
        logger.info(listMemberId.size.toString() + "명 관리자 설정(" + isAdd + ") 처리 (" + principal.name + ")")

        // Field
        var intSuccess = listMemberId.size
        val memberMySelf = getMember(principal.name) ?: return false

        // Exception
        if (memberMySelf.permission != MemberRole.SUPERADMIN) // 권한 제어는 최고 관리자가 아니면 불가능
            return false

        // Process
        for (memberId in listMemberId) {
            // Init
            var member = getMember(memberId)

            // Exception
            if (member == null || member == memberMySelf) {
                intSuccess--
                continue
            }

            // Process
            if (isAdd) {
                if (member.permission == MemberRole.MEMBER)
                    member.permission = MemberRole.ADMIN
            } else if (member.permission == MemberRole.ADMIN)
                member.permission = MemberRole.MEMBER

            // Process - DB Update
            memberRepo.save(member)
        }

        // Return
        return intSuccess > 0
    }

    fun getLoginLog(principal: Principal, intPage: Int, intCount: Int): Page<MemberLoginLog> {
        // Field
        val pageMemberLoginLog: Page<MemberLoginLog>
        val curPage: Pageable

        // Init
        val member: Member = getMember(principal.name) ?: return Page.empty()

        // Init
        curPage = PageRequest.of(intPage - 1, intCount, Sort.by(Sort.Direction.DESC, "logId"))

        // Return
        return memberLoginLogRepo.findByMember(member, curPage)
    }

    fun getLoginLogMaxPage(principal: Principal, intCount: Int): Long {
        // Init
        val member: Member = getMember(principal.name) ?: return 0

        // Process
        val longCount = memberLoginLogRepo.countByMember(member) // userid로 검색
        var intMaxPage = (longCount / intCount).toInt()

        if (longCount % intCount != 0L)
            intMaxPage += 1

        // Return
        return intMaxPage.toLong()
    }

    companion object : KLogging()
}