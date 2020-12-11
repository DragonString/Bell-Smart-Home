package net.softbell.bsh.controller.view.admin

import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author : Bell(bell@softbell.net)
 * @description : 관리자 회원 관리 뷰 컨트롤러
 */
@Controller
@RequestMapping("/admin/member/")
class AdminMemberView {
    // Global Field
    private val G_BASE_REDIRECT_URL: String = "redirect:/admin/member"
    private val G_LOGOUT_REDIRECT_URL: String = "redirect:/logout"

    @Autowired private lateinit var memberService: MemberService

    // 회원 승인 처리
    @PostMapping("approvalNode")
    fun procNodeApproval(model: Model, @AuthenticationPrincipal member: Member,
                         @RequestParam("intMemberId") listMemberId: List<Long>): String {
        // Exception
        if (!memberService.isAdmin(member))
            return G_LOGOUT_REDIRECT_URL

        // Process
        return if (memberService.procMemberApproval(member, listMemberId, isApproval = true, isMember = false))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 회원 승인 처리
    @PostMapping("approvalMember")
    fun procMemberApproval(model: Model, @AuthenticationPrincipal member: Member,
                           @RequestParam("intMemberId") listMemberId: List<Long>): String {
        // Exception
        if (!memberService.isAdmin(member))
            return G_LOGOUT_REDIRECT_URL

        // Process
        return if (memberService.procMemberApproval(member, listMemberId, isApproval = true, isMember = true))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 회원 승인 처리
    @PostMapping("refusal")
    fun procMemberRefusal(model: Model, @AuthenticationPrincipal member: Member,
                          @RequestParam("intMemberId") listMemberId: List<Long>): String {
        // Exception
        if (!memberService.isAdmin(member))
            return G_LOGOUT_REDIRECT_URL

        // Process
        return if (memberService.procMemberApproval(member, listMemberId, isApproval = false, isMember = true))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 회원 정지 처리
    @PostMapping("ban")
    fun procMemberBan(model: Model, @AuthenticationPrincipal member: Member,
                      @RequestParam("intMemberId") listMemberId: List<Long>): String {
        // Exception
        if (!memberService.isAdmin(member))
            return G_LOGOUT_REDIRECT_URL

        // Process
        return if (memberService.procMemberBan(member, listMemberId, true))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 회원 정지 해제 처리
    @PostMapping("unban")
    fun procMemberUnban(model: Model, @AuthenticationPrincipal member: Member,
                        @RequestParam("intMemberId") listMemberId: List<Long>): String {
        // Exception
        if (!memberService.isAdmin(member))
            return G_LOGOUT_REDIRECT_URL

        // Process
        return if (memberService.procMemberBan(member, listMemberId, false))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 회원 권한 상승 처리
    @PostMapping("addAuth")
    fun procMemberAddAuth(model: Model, @AuthenticationPrincipal member: Member,
                          @RequestParam("intMemberId") listMemberId: List<Long>): String {
        // Exception
        if (!memberService.isSuperAdmin(member))
            return G_LOGOUT_REDIRECT_URL

        // Process
        return if (memberService.procSetAdmin(member, listMemberId, true))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 회원 권한 하강 처리
    @PostMapping("delAuth")
    fun procMemberDelAuth(model: Model, @AuthenticationPrincipal member: Member,
                          @RequestParam("intMemberId") listMemberId: List<Long>): String {
        // Exception
        if (!memberService.isAdmin(member))
            return G_LOGOUT_REDIRECT_URL

        // Process
        return if (memberService.procSetAdmin(member, listMemberId, false))
            G_BASE_REDIRECT_URL
        else
            "$G_BASE_REDIRECT_URL?error"
    }

    // 회원탈퇴 처리
    @PostMapping("delete")
    fun execAdminDelete(@AuthenticationPrincipal member: Member,
                        @RequestParam("intMemberId") listMemberId: List<Long>): String {
        // Check
        return if (!memberService.deleteUserList(member, listMemberId))
            "$G_BASE_REDIRECT_URL?error"
        else
            G_BASE_REDIRECT_URL
    }
}