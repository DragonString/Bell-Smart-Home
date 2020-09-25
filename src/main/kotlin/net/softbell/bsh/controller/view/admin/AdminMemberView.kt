package net.softbell.bsh.controller.view.admin

import lombok.AllArgsConstructor
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.service.MemberService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 회원 관리 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/admin/member/")
class AdminMemberView constructor() {
    // Global Field
    private val G_BASE_REDIRECT_URL: String = "redirect:/admin/member"
    private val G_LOGOUT_REDIRECT_URL: String = "redirect:/logout"
    private val memberService: MemberService? = null

    // 회원 승인 처리
    @PostMapping("approvalNode")
    fun procNodeApproval(model: Model?, principal: Principal,
                         @RequestParam("intMemberId") listMemberId: List<Int>): String {
        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (memberService.procMemberApproval(principal, listMemberId, true, false)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 회원 승인 처리
    @PostMapping("approvalMember")
    fun procMemberApproval(model: Model?, principal: Principal,
                           @RequestParam("intMemberId") listMemberId: List<Int>): String {
        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (memberService.procMemberApproval(principal, listMemberId, true, true)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 회원 승인 처리
    @PostMapping("refusal")
    fun procMemberRefusal(model: Model?, principal: Principal,
                          @RequestParam("intMemberId") listMemberId: List<Int>): String {
        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (memberService.procMemberApproval(principal, listMemberId, false, true)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 회원 정지 처리
    @PostMapping("ban")
    fun procMemberBan(model: Model?, principal: Principal,
                      @RequestParam("intMemberId") listMemberId: List<Int>): String {
        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (memberService.procMemberBan(principal, listMemberId, true)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 회원 정지 해제 처리
    @PostMapping("unban")
    fun procMemberUnban(model: Model?, principal: Principal,
                        @RequestParam("intMemberId") listMemberId: List<Int>): String {
        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (memberService.procMemberBan(principal, listMemberId, false)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 회원 권한 상승 처리
    @PostMapping("addAuth")
    fun procMemberAddAuth(model: Model?, principal: Principal,
                          @RequestParam("intMemberId") listMemberId: List<Int>): String {
        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (memberService.procSetAdmin(principal, listMemberId, true)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 회원 권한 하강 처리
    @PostMapping("delAuth")
    fun procMemberDelAuth(model: Model?, principal: Principal,
                          @RequestParam("intMemberId") listMemberId: List<Int>): String {
        // Field
        val member: Member? = memberService!!.getAdminMember(principal.getName())

        // Exception
        if (member == null) return G_LOGOUT_REDIRECT_URL

        // Process
        if (memberService.procSetAdmin(principal, listMemberId, false)) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?error"
    }

    // 회원탈퇴 처리
    @PostMapping("delete")
    fun execAdminDelete(principal: Principal, @RequestParam("intMemberId") listMemberId: List<Int>): String {
        // Check
        if (!memberService!!.deleteUserList(principal, listMemberId)) return G_BASE_REDIRECT_URL + "?error"
        return G_BASE_REDIRECT_URL
    }
}