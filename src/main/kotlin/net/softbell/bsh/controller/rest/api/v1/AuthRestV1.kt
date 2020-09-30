package net.softbell.bsh.controller.rest.api.v1

import io.swagger.annotations.Api
import io.swagger.annotations.ApiParam
import net.softbell.bsh.component.JwtTokenProvider
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증관련 REST API 컨트롤러 V1
 */
@Api(tags = ["1. Auth"])
@RestController
@RequestMapping("/api/rest/v1/auth")
class AuthRestV1 {
    // Global Field
    @Autowired private lateinit var userService: MemberService
    @Autowired private lateinit var responseService: ResponseService
    @Autowired private lateinit var jwtTokenProvider: JwtTokenProvider

    @PostMapping("/login")
    fun procLogin(@ApiParam(value = "회원ID : 이메일", required = true) @RequestParam id: String,
                  @ApiParam(value = "비밀번호", required = true) @RequestParam password: String): ResultDto? {
        // Init
        val member: Member? = userService.loginMember(id, password)

        // Exception
        return if (member == null) responseService.getFailResult(-100, "로그인 실패") else responseService.getSingleResult(jwtTokenProvider.createToken(member.userId, member.authorities))

        // Return
    }
}