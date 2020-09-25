package net.softbell.bsh.service

import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import net.softbell.bsh.domain.AuthStatusRule
import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberLoginLog
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.repository.*
import net.softbell.bsh.dto.request.MemberDto
import net.softbell.bsh.util.BellLog
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
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
class MemberService : UserDetailsService {
    // Global Field
    private val centerService: CenterService? = null
    private val memberRepo: MemberRepo? = null
    private val memberLoginLogRepo: MemberLoginLogRepo? = null
    private val memberInterlockTokenRepo: MemberInterlockTokenRepo? = null
    private val memberGroupItemRepo: MemberGroupItemRepo? = null
    private val nodeActionRepo: NodeActionRepo? = null
    private val nodeActionItemRepo: NodeActionItemRepo? = null
    private val nodeReservRepo: NodeReservRepo? = null
    private val nodeReservActionRepo: NodeReservActionRepo? = null
    private val nodeTriggerRepo: NodeTriggerRepo? = null
    private val nodeTriggerActionRepo: NodeTriggerActionRepo? = null
    @Transactional
    fun joinUser(memberDto: MemberDto): Long {
        // Log
        log.info(BellLog.getLogHead() + "회원가입 요청 (" + memberDto.getUserId() + " - " + memberDto.getUsername() + ")")

        // Field
        val passwordEncoder = BCryptPasswordEncoder()
        val member: Member?

        // Init
        member = getMember(memberDto.getUserId())

        // Exception
        if (member != null) // 같은 아이디로 회원가입이 되어있다면,
        {
            log.info(BellLog.getLogHead() + "member 객체: " + member.getUserId())
            return -1 // 돌아가세요~~
        }
        /*if (memberDto.getPassword().length() < 6 || memberDto.getPassword().length() > 20)
			return -1;
		if (memberDto.getUserId().length() < 4 || memberDto.getPassword().length() > 20)
			return -1;*/

        // Process
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword())) // 비밀번호 암호화
        memberDto.setBan(BanRule.NORMAL.getCode())
        memberDto.setPermission(MemberRole.WAIT.getCode())
        log.info(BellLog.getLogHead() + "가입완료") // TEST #### TODO
        return memberRepo!!.save(memberDto.toEntity()).getMemberId()
    }

    val allMember: MutableList<Member?>
        get() = memberRepo!!.findAll()

    fun getMember(userId: String?): Member? {
        // Field

        // Check

        // Return
        return memberRepo!!.findByUserId(userId) ?: return null
    }

    fun getMember(id: Long): Member? {
        // Field
        val optMember = memberRepo!!.findById(id)

        // Check
        return if (!optMember.isPresent) null else optMember.get()
    }

    fun getAdminMember(userId: String?): Member? {
        // Field
        val member: Member?

        // Init
        member = getMember(userId)

        // Exception
        if (member == null) return null

        // Auth Check
        return if (!isAdmin(member)) null else member
    }

    fun isAdmin(member: Member?): Boolean {
        if (member == null) return false
        return if (!(member.getPermission() === MemberRole.ADMIN ||
                        member.getPermission() === MemberRole.SUPERADMIN) ||
                member.getBan() !== BanRule.NORMAL) false else true
    }

    fun loginMember(id: String?, password: String?): Member? {
        // Field
        val member: Member?
        val passwordEncoder = BCryptPasswordEncoder()

        // Init
        member = getMember(id)

        // Exception
        return if (member == null || !passwordEncoder.matches(password, member.password)) null else member

        // Return
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(userId: String): UserDetails {
        // Field
        val member: Member?

        // Init
        member = getMember(userId)

        // Exsist Check
        if (member == null) throw UsernameNotFoundException("저장된 회원 없음")

        // Login Fail Check
        if (isLoginCancel(member)) throw UsernameNotFoundException("loginFail")

        // DB - Update
        member.setLastLogin(Date())
        memberRepo!!.save(member)

        // Ban Check
        if (isLoginBan(member)) member.setPermission(MemberRole.BAN)

        // Return
        return member
    }

    @Throws(UsernameNotFoundException::class)
    fun tokenLoadUserByUsername(userId: String?): UserDetails {
        // Field
        val member: Member?

        // Init
        member = getMember(userId)

        // Exsist Check
        if (member == null) throw UsernameNotFoundException("저장된 회원 없음")

        // Ban Check
        if (isLoginBan(member)) member.setPermission(MemberRole.BAN)

        // Return
        return member
    }

    @Transactional
    fun procLogin(userId: String?, loginIp: String?, isLogin: Boolean): Boolean {
        // Field
        val member: Member?
        val memberLoginLog: MemberLoginLog

        // Init
        member = getMember(userId)

        // Exception
        if (member == null) return false

        // Process
        memberLoginLog = builder()
                .member(member)
                .requestDate(Date())
                .ipv4(loginIp)
                .build()
        if (isLogin) {
            memberLoginLog.setStatus(AuthStatusRule.SUCCESS)
            member.setLoginFailBanStart(null)
        } else {
            // Field
            val start: Calendar
            val end: Calendar
            val maxFailCount: Int
            val failCheckTime: Int
            val fail: Long

            // Init
            start = Calendar.getInstance()
            end = Calendar.getInstance()
            failCheckTime = centerService.getSetting().getWebLoginFailCheckTime()
            maxFailCount = centerService.getSetting().getWebLoginFailMaxCount()
            start.add(Calendar.SECOND, -failCheckTime)
            memberLoginLog.setStatus(AuthStatusRule.FAIL)
            fail = memberLoginLogRepo!!.countByMemberAndStatusAndRequestDateBetween(member, AuthStatusRule.FAIL, start.time, end.time) + 1

            // Check
            if (fail > maxFailCount) {
                member.setLoginFailBanStart(Date())
                log.info(failCheckTime.toString() + "초 내 로그인 " + fail + "회 실패로 임시 차단 (" + maxFailCount + "회 제한) : " + member.getUserId())
            }
        }

        // Process - DB Save
        memberLoginLogRepo!!.save(memberLoginLog)

        // Return
        return true
    }

    fun isLoginBan(member: Member): Boolean {
        // 1st Check
        if (member.getBan() === BanRule.PERMANENT) return true

        // Field
        val now: Date
        val ban: Date?

        // Init
        now = Date()
        ban = member.getBanDate()

        // 2st Check
        if (member.getBan() === BanRule.TEMP) {
            // Exception
            if (ban == null) return false

            // Check
            return if (now.compareTo(ban) > 0) // 차단 기한이 지났으면
                false else true
        }

        // Return
        return false
    }

    fun isLoginCancel(member: Member): Boolean {
        // Field
        val ban: Calendar
        val banStart: Date?

        // Init
        ban = Calendar.getInstance()
        banStart = member.getLoginFailBanStart() // 로그인 실패 시각 로드

        // Exception
        if (banStart == null) // 차단되지 않았으면
            return false

        // Load
        ban.time = member.getLoginFailBanStart()
        ban.add(Calendar.SECOND, centerService.getSetting().getWebLoginFailBanTime()) // 차단 시각 추가

        // Check
        return if (ban.time.compareTo(Date()) > 0) true else false // 로그인 취소

        // Return
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
        return if (member == null) false else true

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
    fun deleteUser(member: Member?): Boolean {
        // Exception
        if (member == null) return false

        // Field
        val listNodeAction: List<NodeAction?>

        // Init
        listNodeAction = member.getNodeActions()

        // DB - Node Action Child Delete
        nodeReservActionRepo!!.deleteAllByNodeAction(listNodeAction)
        nodeTriggerActionRepo!!.deleteAllByNodeAction(listNodeAction)
        nodeActionItemRepo!!.deleteAllByNodeAction(listNodeAction)

        // DB - Node Delete
        nodeReservRepo!!.deleteByMember(member)
        nodeTriggerRepo!!.deleteByMember(member)
        nodeActionRepo!!.deleteByMember(member)

        // DB - Member Delete
        memberInterlockTokenRepo!!.deleteByMember(member)
        memberLoginLogRepo!!.deleteByMember(member)
        memberGroupItemRepo!!.deleteByMember(member)

        // DB - Post Delete
        memberRepo!!.delete(member)

        // Return
        return true
    }

    @Transactional
    fun deleteUserList(principal: Principal, listMemberId: List<Int>): Boolean {
        // Log
        log.info(BellLog.getLogHead() + "회원 탈퇴 요청 (" + listMemberId.size + ") (" + principal.name + ")")

        // Field
        var isError = false
        val memberMySelf = getMember(principal.name)

        // Init
        for (value in listMemberId) {
            val member = getMember(value.toLong())

            // Exception
            if (member == null || member === memberMySelf) // 해당하는 회원이 없으면
            {
                isError = true
                continue
            }
            if (member.getPermission() === MemberRole.SUPERADMIN && memberMySelf.getPermission() !== MemberRole.SUPERADMIN) // 최고관리자가 아니면 최고관리자 제어 불가
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
    fun modifyInfo(principal: Principal, strCurPassword: String?, strModPassword: String?): Member? {
        // Log
        log.info(BellLog.getLogHead() + "회원정보 수정 요청 (" + principal.name + ")")

        // Field
        var member: Member?
        val passwordEncoder = BCryptPasswordEncoder()

        // Init
        member = getMember(principal.name)

        // Exception
        if (member == null) return null
        /*if (strModPassword.length() < 6 || strModPassword.length() > 20) // 비밀번호 규칙
			return null;*/if (!passwordEncoder.matches(strCurPassword, member.password)) // 현재 등록된 비번이 다르면
            return null

        // Process
        member.setPassword(passwordEncoder.encode(strModPassword)) // 비밀번호 암호화

        // Process - DB Update
        member = memberRepo!!.save(member)

        // Return
        return member
    }

    fun getMemberList(intPage: Int, intCount: Int): Page<Member?> {
        // Field
        val pageMember: Page<Member?>
        val curPage: Pageable

        // Init
        curPage = PageRequest.of(intPage - 1, intCount, Sort(Sort.Direction.DESC, "memberId"))
        pageMember = memberRepo!!.findAll(curPage)

        // Return
        return pageMember
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
    fun procMemberApproval(principal: Principal, listMemberId: List<Int>, isApproval: Boolean, isMember: Boolean): Boolean {
        // Log
        log.info(BellLog.getLogHead() + listMemberId.size + "명 회원 승인(" + isApproval + ") 처리 (" + principal.name + ")")

        // Field
        var intSuccess = listMemberId.size
        val memberMySelf: Member?

        // Init
        memberMySelf = getAdminMember(principal.name)

        // Exception
        if (memberMySelf == null) return false

        // Process
        for (intMemberId in listMemberId) {
            // Field
            var member: Member?

            // Init
            member = getMember(intMemberId.toLong())

            // Exception
            if (member == null) {
                intSuccess--
                continue
            }
            if (member.getPermission() !== MemberRole.WAIT) // 승인 대기중 회원만 제어 가능
                continue

            // Process
            if (isApproval) if (isMember) member.setPermission(MemberRole.MEMBER) else member.setPermission(MemberRole.NODE) else member.setPermission(MemberRole.BAN)

            // Process - DB Update
            memberRepo!!.save(member)
        }

        // Return
        return if (intSuccess <= 0) false else true
    }

    fun procMemberBan(principal: Principal, listMemberId: List<Int>, isBan: Boolean): Boolean {
        // Log
        log.info(BellLog.getLogHead() + listMemberId.size + "명 회원 정지(" + isBan + ") 처리 (" + principal.name + ")")

        // Field
        var intSuccess = listMemberId.size
        val memberMySelf: Member?

        // Init
        memberMySelf = getMember(principal.name)

        // Exception
        if (memberMySelf == null) return false

        // Process
        for (intMemberId in listMemberId) {
            // Field
            var member: Member?

            // Init
            member = getMember(intMemberId.toLong())

            // Exception
            if (member == null || member === memberMySelf) {
                intSuccess--
                continue
            }
            if (member.getPermission() === MemberRole.SUPERADMIN && memberMySelf.getPermission() !== MemberRole.SUPERADMIN) // 최고관리자가 아니면 최고관리자 제어 불가
                continue

            // Process
            if (isBan) member.setBan(BanRule.PERMANENT) else member.setBan(BanRule.NORMAL)

            // Process - DB Update
            memberRepo!!.save(member)
        }

        // Return
        return if (intSuccess <= 0) false else true
    }

    fun procSetAdmin(principal: Principal, listMemberId: List<Int>, isAdd: Boolean): Boolean {
        // Log
        log.info(BellLog.getLogHead() + listMemberId.size + "명 관리자 설정(" + isAdd + ") 처리 (" + principal.name + ")")

        // Exception


        // Field
        var intSuccess = listMemberId.size
        val memberMySelf = getMember(principal.name)

        // Exception
        if (memberMySelf.getPermission() !== MemberRole.SUPERADMIN) // 권한 제어는 최고 관리자가 아니면 불가능
            return false

        // Process
        for (intMemberId in listMemberId) {
            // Field
            var member: Member?

            // Init
            member = getMember(intMemberId.toLong())

            // Exception
            if (member == null || member === memberMySelf) {
                intSuccess--
                continue
            }

            // Process
            if (isAdd) {
                if (member.getPermission() === MemberRole.MEMBER) member.setPermission(MemberRole.ADMIN)
            } else if (member.getPermission() === MemberRole.ADMIN) member.setPermission(MemberRole.MEMBER)

            // Process - DB Update
            memberRepo!!.save(member)
        }

        // Return
        return if (intSuccess <= 0) false else true
    }

    fun getLoginLog(principal: Principal, intPage: Int, intCount: Int): Page<MemberLoginLog?>? {
        // Field
        val member: Member?
        val pageMemberLoginLog: Page<MemberLoginLog?>?
        val curPage: Pageable

        // Init
        member = getMember(principal.name)

        // Exception
        if (member == null) return null

        // Init
        curPage = PageRequest.of(intPage - 1, intCount, Sort(Sort.Direction.DESC, "logId"))
        pageMemberLoginLog = memberLoginLogRepo!!.findByMember(member, curPage)

        // Process

        // Return
        return pageMemberLoginLog
    }

    fun getLoginLogMaxPage(principal: Principal, intCount: Int): Long {
        // Field
        val member: Member?
        val longCount: Long
        var intMaxPage: Int

        // Init
        member = getMember(principal.name)

        // Exception
        if (member == null) return 0

        // Process
        longCount = memberLoginLogRepo!!.countByMember(member) // userid로 검색
        intMaxPage = (longCount / intCount).toInt()
        if (longCount % intCount != 0L) intMaxPage += 1

        // Return
        return intMaxPage.toLong()
    }
}