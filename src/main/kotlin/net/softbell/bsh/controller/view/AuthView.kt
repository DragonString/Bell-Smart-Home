package net.softbell.bsh.controller.view

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.dto.request.InterlockTokenDto
import net.softbell.bsh.dto.view.MemberProfileCardDto
import net.softbell.bsh.service.InterlockService
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 계정 인증 뷰 컨트롤러
 */
@Controller
@RequestMapping("/member")
class AuthView {
    // Global Field
    private val G_BASE_REDIRECT_URL: String = "redirect:/member"
    private val G_LOGOUT_REDIRECT_URL: String = "redirect:/logout"

    @Autowired private lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var interlockService: InterlockService

    // 내 정보 페이지
    @GetMapping("/profile")
    fun dispMyInfo(model: Model, @AuthenticationPrincipal member: Member): String {
        // Process
        model.addAttribute("cardMemberProfile", MemberProfileCardDto(member))
        //    	if (memberService.checkDelete(principal.getName()))
//    			model.addAttribute("checkDelete", "1");

        // Return
        return "services/auth/Profile"
    }

    // 내 정보 수정 페이지
    @GetMapping("/modify")
    fun dispMyInfoModify(model: Model, @AuthenticationPrincipal member: Member): String {
        // Return
        return "services/auth/Modify"
    }

    // 내 정보 수정 처리
    @PostMapping("/modify")
    fun procMyInfoModify(model: Model, @AuthenticationPrincipal member: Member,
                         @RequestParam("curPassword") strCurPassword: String,
                         @RequestParam("modPassword") strModPassword: String): String {
        // Init
        val isSuccess = memberService.modifyInfo(member, strCurPassword, strModPassword)

        // Return
        return if (!isSuccess)
            "$G_BASE_REDIRECT_URL/modify?error"
        else
            G_LOGOUT_REDIRECT_URL
    }

    /*
    // 회원탈퇴 처리
    @PostMapping("/delete")
    public String execDelete(MemberInfoDTO memberDto)
    {
    	// Check
        if (!memberService.deleteUser(memberDto))
        	return "redirect:/member/info?error";

        return "redirect:/logout";
    }
*/
    // 로그인 로그 페이지
    @GetMapping("/log")
    fun dispLoginLog(model: Model, @AuthenticationPrincipal member: Member,
                     @RequestParam(value = "page", required = false, defaultValue = "1") intPage: Int,
                     @RequestParam(value = "count", required = false, defaultValue = "100") intCount: Int): String {
        // Auth Check
        var page = intPage
        var count = intCount

        // Exception
        if (page < 1) page = 1
        if (count < 1) count = 1

        // Init
        val pageMemberLoginLog = memberService.getLoginLog(member, page, count)

        // Process
        model.addAttribute("listCardActivityLogs", viewDtoConverterService.convMemberActivityLogCards(pageMemberLoginLog.content))
        //		model.addAttribute("logCurPage", intPage);
//		model.addAttribute("logPageCount", intCount);
//    	model.addAttribute("logMaxPage", memberService.getLoginLogMaxPage(principal, intCount));

        // Return
        return "services/auth/LoginLog"
    }

    @GetMapping("/interlock")
    fun dispInterlock(model: Model, @AuthenticationPrincipal member: Member): String {
        // Init
        val listMemberInterlockToken = interlockService.getAllTokens(member)

        // Process
        model.addAttribute("listCardTokens", viewDtoConverterService.convInterlockTokenCards(listMemberInterlockToken))

        // Return
        return "services/auth/Interlock"
    }

    @PostMapping("/interlock/create")
    fun createInterlock(@AuthenticationPrincipal member: Member, interlockTokenDto: InterlockTokenDto): String {
        // Process
        val isSuccess = interlockService.createToken(member, interlockTokenDto)

        // Return
        return if (isSuccess)
            "$G_BASE_REDIRECT_URL/interlock"
        else
            "$G_BASE_REDIRECT_URL/interlock?err"
    }

    @PostMapping("/interlock/enable/{id}")
    fun enableToken(@AuthenticationPrincipal member: Member, @PathVariable("id") tokenId: Long): String {
        // Process
        val isSuccess = interlockService.modifyToken(member, tokenId, EnableStatusRule.ENABLE)

        // Return
        return if (isSuccess)
            "$G_BASE_REDIRECT_URL/interlock"
        else
            "$G_BASE_REDIRECT_URL/interlock?err"
    }

    @PostMapping("/interlock/disable/{id}")
    fun disableToken(@AuthenticationPrincipal member: Member, @PathVariable("id") tokenId: Long): String {
        // Process
        val isSuccess = interlockService.modifyToken(member, tokenId, EnableStatusRule.DISABLE)

        // Return
        return if (isSuccess)
            "$G_BASE_REDIRECT_URL/interlock"
        else
            "$G_BASE_REDIRECT_URL/interlock?err"
    }

    @PostMapping("/interlock/delete/{id}")
    fun deleteToken(@AuthenticationPrincipal member: Member, @PathVariable("id") tokenId: Long): String {
        // Process
        val isSuccess = interlockService.deleteToken(member, tokenId)

        // Return
        return if (isSuccess)
            "$G_BASE_REDIRECT_URL/interlock"
        else
            "$G_BASE_REDIRECT_URL/interlock?err"
    }
}