package net.softbell.bsh.controller.rest.api.v1

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import lombok.RequiredArgsConstructor
import net.softbell.bsh.component.JwtTokenProvider
import net.softbell.bsh.dto.response.SingleResultDto
import net.softbell.bsh.service.ResponseService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 유저 정보 관련 REST API 컨트롤러 V1
 */
@Api(tags = ["2. User"])
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/user")
class UserRestV1 constructor() {
    // Global Field
    private val responseService: ResponseService? = null
    private val jwtTokenProvider: JwtTokenProvider? = null
    @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "access_token", required = true, dataType = "String", paramType = "header")
    @ApiOperation(value = "인증 토큰 재발급", notes = "인증 토큰 만료기간 연장")
    @GetMapping(value = ["/regenToken"])
    fun regenToken(auth: Authentication): SingleResultDto<String?>? {
        return responseService!!.getSingleResult(jwtTokenProvider!!.createToken(auth.getName(), auth.getAuthorities()))
    }
}