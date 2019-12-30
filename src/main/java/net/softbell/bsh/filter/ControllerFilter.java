package net.softbell.bsh.filter;

import java.security.Principal;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 컨트롤러가 동작하기 전 필터 클래스
 */
public class ControllerFilter {
	// Static Global Field
	public static final String G_SERVICE_ERROR_REDIRECT_URL = "redirect:/error/service";
	public static final String G_LOGOUT_REDIRECT_URL = "redirect:/logout";
	public static final String G_BAN_REDIRECT_URL = "redirect:/denied";
		
	public void FilterModelPrincipal(Model model, Principal principal)
	{
		// Username
		if (principal != null)
			model.addAttribute("USER_NAME", principal.getName());
	}
	
	public ModelAndView FilterModelAndViewPrincipal(ModelAndView model, Principal principal)
	{
		// Username
		if (principal != null)
			model.addObject("USER_NAME", principal.getName());
		
		return model;
	}
}
