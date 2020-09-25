package net.softbell.bsh.controller.rest.api.v1

import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import lombok.AllArgsConstructor
import net.softbell.bsh.component.JwtTokenProvider
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.ResponseService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증관련 REST API 컨트롤러 V1
 */
@Api(tags = ["1. Auth"])
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/auth")
class AuthRestV1 constructor() {
    // Global Field
    private val userService: MemberService? = null
    private val responseService: ResponseService? = null
    private val jwtTokenProvider: JwtTokenProvider? = null
    @PostMapping("/login")
    fun procLogin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam id: String?,
                  @ApiParam(value = "비밀번호", required = true) @RequestParam password: String?): ResultDto? {
        // Field
        val member: Member?

        // Init
        member = userService!!.loginMember(id, password)

        // Exception
        if (member == null) return responseService!!.getFailResult(-100, "로그인 실패")

        // Return
        return responseService!!.getSingleResult<T?>(jwtTokenProvider!!.createToken(member.getUserId(), member.getAuthorities()))
    }
}