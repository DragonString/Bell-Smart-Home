package net.softbell.bsh.controller.rest.api.v1;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/v1")
public class UserController
{
    @ApiImplicitParams({
            @ApiImplicitParam(name="X-AUTH-TOKEN", value="로그인 성공 후 access_token", required=true, dataType="String", paramType="header")
    })
    @ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
    @RequestMapping(value = "/users")
    public String findAllUser()
    {
        // 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
    	System.out.println("???");
        return "USERS";
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="X-AUTH-TOKEN", value="로그인 성공 후 access_token", required=true, dataType="String", paramType="header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원번호(msrl)로 회원을 조회한다")
    @GetMapping(value = "/user")
    public String findUser() {
        // SecurityContext에서 인증받은 회원의 정보를 얻어온다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        // 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
        return id;
    }
}

