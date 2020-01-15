package net.softbell.bsh.controller.rest.api.v1;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import net.softbell.bsh.component.JwtTokenProvider;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.service.MemberService;
import net.softbell.bsh.service.ResponseService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증관련 REST API 컨트롤러 V1
 */
@Api(tags = {"1. Auth"})
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/auth")
public class AuthRestV1
{
	// Global Field
    private final MemberService userService;
    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;
   
    @PostMapping("/login")
    public ResultDto procLogin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
    							@ApiParam(value = "비밀번호", required = true) @RequestParam String password)
    {
    	// Field
    	Member member;
    	
    	// Init
    	member = userService.loginMember(id, password);
    	
    	// Exception
    	if (member == null)
    		return responseService.getFailResult(-100, "로그인 실패");

    	// Return
    	return responseService.getSingleResult(jwtTokenProvider.createToken(member.getUserId(), member.getAuthorities()));
    }
}
