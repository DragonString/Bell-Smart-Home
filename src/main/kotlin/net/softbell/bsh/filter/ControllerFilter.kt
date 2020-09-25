package net.softbell.bsh.filter

import org.springframework.ui.Model
import org.springframework.web.servlet.ModelAndView
import java.security.Principal

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 컨트롤러가 동작하기 전 필터 클래스
 */
class ControllerFilter {
    fun FilterModelPrincipal(model: Model, principal: Principal?) {
        // Username
        if (principal != null) model.addAttribute("USER_NAME", principal.name)
    }

    fun FilterModelAndViewPrincipal(model: ModelAndView, principal: Principal?): ModelAndView {
        // Username
        if (principal != null) model.addObject("USER_NAME", principal.name)
        return model
    }

    companion object {
        // Static Global Field
        const val G_SERVICE_ERROR_REDIRECT_URL = "redirect:/error/service"
        const val G_LOGOUT_REDIRECT_URL = "redirect:/logout"
        const val G_BAN_REDIRECT_URL = "redirect:/denied"
    }
}