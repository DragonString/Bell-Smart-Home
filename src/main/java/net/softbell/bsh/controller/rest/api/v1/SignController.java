package net.softbell.bsh.controller.rest.api.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.softbell.bsh.dto.response.CommonResult;
import net.softbell.bsh.dto.response.SingleResult;
import net.softbell.bsh.handler.security.JwtTokenProvider;
import net.softbell.bsh.service.ResponseService;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/v1")
public class SignController {

    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @RequestMapping(value = "/signin")
    public SingleResult<String> signin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                                       @ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
    	List<String> list = new ArrayList<String>();
    	list.add("Role");
    	System.out.println("로그인 와지긴 함?");
    	return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf("id"), list));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping(value = "/signup")
    public CommonResult signup(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam String id,
                               @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
                               @ApiParam(value = "이름", required = true) @RequestParam String name) {

    	return responseService.getSuccessResult();
    }
}
