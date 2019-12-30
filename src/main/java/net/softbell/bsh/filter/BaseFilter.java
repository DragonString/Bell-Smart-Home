package net.softbell.bsh.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import net.softbell.bsh.dto.navbar.NavBar;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : request 최초, response 최후에 동작하는 필터 클래스
 */
@Component
@Order(1)
public class BaseFilter implements Filter {
//	@Autowired
//	LocaleResolver localeResolver;

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	// Navigation Bar
    	setNavBar(servletRequest);
    	
    	// Next Filter
    	filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    	
    }
    
    private void setNavBar(ServletRequest servletRequest)
	{
		List<Object> navbar = new ArrayList<Object>();
		
		List<NavBar> left_nav_item = new ArrayList<NavBar>();
		List<NavBar> nav_drop_profile = new ArrayList<NavBar>();
		List<NavBar> right_nav_item = new ArrayList<NavBar>();
		
		nav_drop_profile.add(new NavBar("Preference", "/preference", NavBar.NavType.LINK));
		nav_drop_profile.add(new NavBar("", "", NavBar.NavType.DIVIDER));
		nav_drop_profile.add(new NavBar("Logout", "/logout", NavBar.NavType.LINK));
		
		left_nav_item.add(new NavBar("Dash Board", "/dashboard", NavBar.NavType.LINK, true, null));
		left_nav_item.add(new NavBar("Monitor", "/monitor", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("Control", "/control", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("Setting", "/setting", NavBar.NavType.LINK));
		
		left_nav_item.add(new NavBar("LED ON", "/iot/led?mode=circuit&value=on", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("LED OFF", "/iot/led?mode=circuit&value=off", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("OFF", "/iot/led?mode=bar&value=off", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("RED", "/iot/led?mode=bar&value=red", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("GREEN", "/iot/led?mode=bar&value=green", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("BLUE", "/iot/led?mode=bar&value=blue", NavBar.NavType.LINK));
		left_nav_item.add(new NavBar("WHITE", "/iot/led?mode=bar&value=white", NavBar.NavType.LINK));
		
		right_nav_item.add(new NavBar("Profile", "navDrop", NavBar.NavType.DROPDOWN, false, nav_drop_profile));
		right_nav_item.add(new NavBar("Admin", "/admin", NavBar.NavType.DISABLED));
		
		navbar.add(left_nav_item);
		navbar.add(right_nav_item);
		
		servletRequest.setAttribute("nav_brand", "BSH");
		servletRequest.setAttribute("navbar", navbar);
	}
}
