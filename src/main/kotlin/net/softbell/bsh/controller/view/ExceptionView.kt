package net.softbell.bsh.controller.view

import mu.KLogging
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

/**
 * @author : Bell(bell@softbell.net)
 * @description : 예외 페이지 컨트롤러
 */
@Controller
class ExceptionView : ErrorController {
    override fun getErrorPath(): String {
        return ERROR_PATH
    }

    @RequestMapping(ERROR_PATH)
    fun handleError(request: HttpServletRequest, redirectAttributes: RedirectAttributes): String {
        // Field
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)
        val httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()))
        val strCode = status.toString()
        var strMessage = httpStatus.reasonPhrase

        // Log
        logger.warn("에러 페이지 발생 (code: $strCode)")

        // Init
        if (strCode.equals("400", ignoreCase = true))
            strMessage = "비 정상적인 요청입니다."
        else if (strCode.equals("404", ignoreCase = true))
            strMessage = "존재하지 않는 페이지 입니다."

        // Data Send
        redirectAttributes.addFlashAttribute("code", strCode)
        redirectAttributes.addFlashAttribute("msg", strMessage)

        // Process
        logger.info("httpStatus : $httpStatus")

        // Return
        return "redirect:/err"
    }

    @RequestMapping("/err")
    fun defaultError(): String {
        // Return
        return G_ERROR_DEFAULT_PATH + "Default"
    }

    // 접근 거부 페이지
    @GetMapping("/denied")
    fun dispDenied(model: Model): String {
        // Init
        //FilterModelPrincipal(model, principal);

        // Return
        return "$G_ERROR_DEFAULT_PATH/Denied"
    }

    companion object : KLogging() {
        // Global Field
        private const val G_ERROR_DEFAULT_PATH: String = "services/error/"
        private const val ERROR_PATH: String = "/error"
    }
}