package net.softbell.bsh.controller.rest.api

import net.softbell.bsh.dto.response.ResultDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예외페이지 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api/rest/exception")
class ExceptionRest {
    @GetMapping("/entrypoint")
    fun entrypointException(): ResultDto {
        var resultDto: ResultDto = ResultDto()

        resultDto.success = false
        resultDto.code = -200
        resultDto.message = "인증실패"

        return resultDto
    }

    @GetMapping("/denied")
    fun accessdeniedException(): ResultDto {
        var resultDto: ResultDto = ResultDto()

        resultDto.success = false
        resultDto.code = -201
        resultDto.message = "권한부족"

        return resultDto
    }
}