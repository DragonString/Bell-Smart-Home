package net.softbell.bsh.controller.rest.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.dto.response.ResultDto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예외페이지 REST API 컨트롤러
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/exception")
public class ExceptionRest
{
    @GetMapping("/entrypoint")
    public ResultDto entrypointException()
    {
        return ResultDto.builder()
        					.success(false)
        					.code(-200)
        					.message("인증실패")
        						.build();
    }

    @GetMapping("/denied")
    public ResultDto accessdeniedException()
    {
    	return ResultDto.builder()
				.success(false)
				.code(-201)
				.message("권한부족")
					.build();
    }
}
