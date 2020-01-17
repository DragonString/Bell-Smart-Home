package net.softbell.bsh.controller.view;

import java.security.Principal;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 커스텀 에러 컨트롤러
 */
@Slf4j
@Controller
public class CustomErrorController implements ErrorController
{
	// Global Field
	public static final String G_ERROR_DEFAULT_PATH = "services/error/default";
	private static final String ERROR_PATH = "/error";
	
	@Override
	public String getErrorPath()
	{
		return ERROR_PATH;
	}

	@RequestMapping(ERROR_PATH)
	public String handleError(HttpServletRequest request, Principal principal, RedirectAttributes redirectAttributes)
	{
		// Field
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));
		String strCode = status.toString();
		String strMessage = httpStatus.getReasonPhrase();
		
		// Log
		log.warn(BellLog.getLogHead() + "에러 페이지 발생 (code: " + strCode + ")");
		
		// Init
		if (strCode.equalsIgnoreCase("400"))
			strMessage = "비 정상적인 요청입니다.";
		else if (strCode.equalsIgnoreCase("404"))
			strMessage = "존재하지 않는 페이지 입니다.";
		
		// Data Send
		redirectAttributes.addFlashAttribute("code", strCode);
		redirectAttributes.addFlashAttribute("msg", strMessage);
		
		// Process
		log.info(BellLog.getLogHead() + "httpStatus : " + httpStatus.toString());
		
		// Return
		return "redirect:/err";
	}
	
	@RequestMapping("/err")
	public String defaultError()
	{
		// Return
		return G_ERROR_DEFAULT_PATH;
	}
}
