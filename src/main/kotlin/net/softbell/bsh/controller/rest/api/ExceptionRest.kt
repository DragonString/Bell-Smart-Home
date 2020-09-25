package net.softbell.bsh.controller.rest.api

import lombok.RequiredArgsConstructor
import net.softbell.bsh.dto.response.ResultDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예외페이지 REST API 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/exception")
class ExceptionRest constructor() {
    @GetMapping("/entrypoint")
    fun entrypointException(): ResultDto {
        return builder()
                .success(false)
                .code(-200)
                .message("인증실패")
                .build()
    }

    @GetMapping("/denied")
    fun accessdeniedException(): ResultDto {
        return builder()
                .success(false)
                .code(-201)
                .message("권한부족")
                .build()
    }
}