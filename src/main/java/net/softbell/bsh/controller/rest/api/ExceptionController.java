package net.softbell.bsh.controller.rest.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.dto.response.CommonResult;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/exception")
public class ExceptionController {

    @GetMapping("/entrypoint")
    public CommonResult entrypointException()
    {
        return CommonResult.builder()
        					.success(false)
        					.code(-200)
        					.msg("인증실패")
        						.build();
    }

    @GetMapping("/denied")
    public CommonResult accessdeniedException()
    {
    	return CommonResult.builder()
				.success(false)
				.code(-201)
				.msg("권한부족")
					.build();
    }
}
