package net.softbell.bsh.controller.view

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberInterlockToken
import net.softbell.bsh.domain.entity.MemberLoginLog
import net.softbell.bsh.dto.request.InterlockTokenDto
import net.softbell.bsh.dto.view.MemberProfileCardDto
import net.softbell.bsh.service.InterlockService
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import java.security.Principal

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 계정 인증 뷰 컨트롤러
 */
@Controller
@RequestMapping("/member")
class AuthView constructor() {
    // Global Field
    private val G_BASE_PATH: String = "services/auth"
    private val G_BASE_REDIRECT_URL: String = "redirect:/member"
    private val G_LOGOUT_REDIRECT_URL: String = "redirect:/logout"

    @Autowired lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var interlockService: InterlockService

    // 내 정보 페이지
    @GetMapping("/profile")
    fun dispMyInfo(model: Model, principal: Principal): String {
        // Auth Check
        if (memberService!!.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
            return G_LOGOUT_REDIRECT_URL

        // Field
        val member: Member?

        // Init
        member = memberService.getMember(principal.getName())

        // Process
        model.addAttribute("cardMemberProfile", MemberProfileCardDto(member))
        //    	if (memberService.checkDelete(principal.getName()))
//    			model.addAttribute("checkDelete", "1");

        // Return
        return G_BASE_PATH + "/Profile"
    }

    // 내 정보 수정 페이지
    @GetMapping("/modify")
    fun dispMyInfoModify(model: Model?, principal: Principal): String {
        // Auth Check
        if (memberService!!.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
            return G_LOGOUT_REDIRECT_URL

        // Return
        return G_BASE_PATH + "/Modify"
    }

    // 내 정보 수정 처리
    @PostMapping("/modify")
    fun procMyInfoModify(model: Model?, principal: Principal,
                         @RequestParam("curPassword") strCurPassword: String?,
                         @RequestParam("modPassword") strModPassword: String?): String {
        // Auth Check
        if (memberService!!.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
            return G_LOGOUT_REDIRECT_URL

        // Field
        val member: Member?

        // Init
        member = memberService.modifyInfo(principal, strCurPassword, strModPassword)

        // Process
        if (member == null) return "redirect:/member/modify?error"

        // Return
        return G_LOGOUT_REDIRECT_URL
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
    fun dispLoginLog(model: Model, principal: Principal,
                     @RequestParam(value = "page", required = false, defaultValue = "1") intPage: Int,
                     @RequestParam(value = "count", required = false, defaultValue = "100") intCount: Int): String {
        // Auth Check
        var intPage: Int = intPage
        var intCount: Int = intCount
        if (memberService!!.getMember(principal.getName()) == null) // 회원 정보가 존재하지 않으면 로그아웃 처리
            return G_LOGOUT_REDIRECT_URL

        // Exception
        if (intPage < 1) intPage = 1
        if (intCount < 1) intCount = 1

        // Field
        val pageMemberLoginLog: Page<MemberLoginLog?>?

        // Init
        pageMemberLoginLog = memberService.getLoginLog(principal, intPage, intCount)

        // Process
        model.addAttribute("listCardActivityLogs", viewDtoConverterService!!.convMemberActivityLogCards(pageMemberLoginLog!!.getContent()))
        //		model.addAttribute("logCurPage", intPage);
//		model.addAttribute("logPageCount", intCount);
//    	model.addAttribute("logMaxPage", memberService.getLoginLogMaxPage(principal, intCount));

        // Return
        return G_BASE_PATH + "/LoginLog"
    }

    @GetMapping("/interlock")
    fun dispInterlock(model: Model, auth: Authentication): String {
        // Field
        val listMemberInterlockToken: List<MemberInterlockToken?>?

        // Init
        listMemberInterlockToken = interlockService!!.getAllTokens(auth)

        // Process
        model.addAttribute("listCardTokens", viewDtoConverterService!!.convInterlockTokenCards(listMemberInterlockToken))

        // Return
        return G_BASE_PATH + "/Interlock"
    }

    @PostMapping("/interlock/create")
    fun createInterlock(auth: Authentication, interlockTokenDto: InterlockTokenDto): String {
        // Field
        val isSuccess: Boolean

        // Process
        isSuccess = interlockService!!.createToken(auth, interlockTokenDto)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/interlock" else return G_BASE_REDIRECT_URL + "/interlock?err"
    }

    @PostMapping("/interlock/enable/{id}")
    fun enableToken(auth: Authentication, @PathVariable("id") tokenId: Long): String {
        // Field
        val isSuccess: Boolean

        // Process
        isSuccess = interlockService!!.modifyToken(auth, tokenId, EnableStatusRule.ENABLE)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/interlock" else return G_BASE_REDIRECT_URL + "/interlock?err"
    }

    @PostMapping("/interlock/disable/{id}")
    fun disableToken(auth: Authentication, @PathVariable("id") tokenId: Long): String {
        // Field
        val isSuccess: Boolean

        // Process
        isSuccess = interlockService!!.modifyToken(auth, tokenId, EnableStatusRule.DISABLE)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/interlock" else return G_BASE_REDIRECT_URL + "/interlock?err"
    }

    @PostMapping("/interlock/delete/{id}")
    fun deleteToken(auth: Authentication, @PathVariable("id") tokenId: Long): String {
        // Field
        val isSuccess: Boolean

        // Process
        isSuccess = interlockService!!.deleteToken(auth, tokenId)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/interlock" else return G_BASE_REDIRECT_URL + "/interlock?err"
    }
}