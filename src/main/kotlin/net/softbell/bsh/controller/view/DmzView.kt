package net.softbell.bsh.controller.view

import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.dto.request.MemberDto
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

/**
 * @author : Bell(bell@softbell.net)
 * @description : 계정 비 인증 뷰 컨트롤러
 */
@Controller
class DmzView {
    // Global Field
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var centerService: CenterService

    // 회원가입 페이지
    @GetMapping("/signup")
    fun dispSignup(model: Model, @AuthenticationPrincipal member: Member?): String {
        // Auth Check
        if (member != null) // 회원 정보가 존재하면 메인 화면으로 이동 (로그아웃부터 하셈)
            return "redirect:/"

        // Process
        model.addAttribute("register", centerService.setting.webRegister)

        // Return
        return "services/dmz/Signup"
    }

    // 회원가입 처리
    @PostMapping("/signup")
    fun execSignup(memberDTO: MemberDto): String {
        // Exception
        if (centerService.setting.webRegister == 0.toByte())
            return "redirect:/signup?error" // TODO 회원가입 금지 설정시 리다이렉션

        // Init
        val intResult = memberService.joinUser(memberDTO)

        // Check
        return if (intResult == -1L)
            "redirect:/signup?error"
        else
            "redirect:/login"
    }

    // 로그인 페이지
    @GetMapping("/login")
    fun dispLogin(model: Model, @AuthenticationPrincipal member: Member?): String {
        // Auth Check
        if (member != null) // 회원 정보가 존재하면 메인 화면으로 이동 (로그아웃부터 하셈)
            return "redirect:/"

        // Process
        model.addAttribute("maintenance", centerService.setting.webMaintenance)

        // Return
        return "services/dmz/Login"
    }
}