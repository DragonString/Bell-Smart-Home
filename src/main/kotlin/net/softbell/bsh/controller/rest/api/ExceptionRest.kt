package net.softbell.bsh.controller.rest.api

import net.softbell.bsh.dto.response.ResultDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : Bell(bell@softbell.net)
 * @description : 예외페이지 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/rest/exception")
class ExceptionRest {
    @GetMapping("/entrypoint")
    fun entrypointException(): ResultDto {
        return ResultDto(
                success = false,
                code = -200,
                message = "인증실패"
        )
    }

    @GetMapping("/denied")
    fun accessdeniedException(): ResultDto {
        return ResultDto(
                success = false,
                code = -201,
                message = "권한부족"
        )
    }
}