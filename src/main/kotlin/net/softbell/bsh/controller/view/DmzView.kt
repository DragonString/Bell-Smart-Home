package net.softbell.bsh.controller.view

import net.softbell.bsh.dto.request.MemberDto
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 계정 비 인증 뷰 컨트롤러
 */
@Controller
class DmzView constructor() {
    // Global Field
    private val G_BASE_PATH: String = "services/dmz"

    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var centerService: CenterService

    // 회원가입 페이지
    @GetMapping("/signup")
    fun dispSignup(model: Model, principal: Principal?): String {
        // Init
        //FilterModelPrincipal(model, principal);

        // Auth Check
        if (principal != null) // 회원 정보가 존재하면 메인 화면으로 이동 (로그아웃부터 하셈)
            return "redirect:/"

        // Process
        model.addAttribute("register", centerService.getSetting().getWebRegister())

        // Return
        return G_BASE_PATH + "/Signup"
    }

    // 회원가입 처리
    @PostMapping("/signup")
    fun execSignup(memberDTO: MemberDto): String {
        // Field
        val intResult: Long

        // Exception
        if (centerService.getSetting().getWebRegister() === 0);

        // Init
        intResult = memberService!!.joinUser(memberDTO)

        // Check
        if (intResult == -1L) return "redirect:/signup?error"
        return "redirect:/login"
    }

    // 로그인 페이지
    @GetMapping("/login")
    fun dispLogin(model: Model, principal: Principal?, request: HttpServletRequest?, response: HttpServletResponse?): String {
        // Init
        //FilterModelPrincipal(model, principal);

        // Auth Check
        if (principal != null) // 회원 정보가 존재하면 메인 화면으로 이동 (로그아웃부터 하셈)
            return "redirect:/"

        // Process
        model.addAttribute("maintenance", centerService.getSetting().getWebMaintenance())

        // Return
        return G_BASE_PATH + "/Login"
    }
}