package net.softbell.bsh.controller.view;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 테스트 페이지 뷰 컨트롤러
 */
@Controller
@RequestMapping("/test/")
public class TestView {
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("bs_test")
    public String bs_test(Model model){
        model.addAttribute("datetime", new Date());
        model.addAttribute("username", "test");
        model.addAttribute("mode", "앱모드");

        //return "bs_test";
        return "NavBar_Example";
    }
	
	@GetMapping("chat")
	public String chat(Model model)
	{
		G_Logger.info(BellLog.getLogHead() + "chat call!!");
		
		return "test/room";
	}
	
	@GetMapping("user")
	public String test(Model model)
	{
		G_Logger.info(BellLog.getLogHead() + "user/test call!!");
		
		return "home";
	}
		
	@GetMapping({"temp"})
	public String getTest(Model model)
	{
		Test user = new Test("kkaok", "테스트", "web") ;
        model.addAttribute("user", user);
        return "test";
	}
	
	@Setter
	@Getter
	private class Test
	{
		private String userId;
	    private String userPwd;
	    private String name;
	    private String authType;
	    
	    public Test(String userId, String name, String authType) {
	        super();
	        this.userId = userId;
	        this.name = name;
	        this.authType = authType;
	    }
	    
	    @Override
	    public String toString() {
	        return "User [userId=" + userId + ", userPwd=" + userPwd + ", name=" + name + ", authType=" + authType + "]";
	    }
	}
}
