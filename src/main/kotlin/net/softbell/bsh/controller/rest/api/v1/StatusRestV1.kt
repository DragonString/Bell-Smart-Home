package net.softbell.bsh.controller.rest.api.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import net.softbell.bsh.dto.response.SingleResultDto;
import net.softbell.bsh.service.ResponseService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 상태관련 REST API 컨트롤러 V1
 */
@Api(tags = {"0. Status"})
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/status")
public class StatusRestV1
{
	// Global Field
    private final ResponseService responseService;
    
    @GetMapping("/server")
    public SingleResultDto<String> checkServer()
    {
    	return responseService.getSingleResult("normal");
    }
}
