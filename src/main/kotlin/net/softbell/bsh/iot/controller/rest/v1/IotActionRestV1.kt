package net.softbell.bsh.iot.controller.rest.v1

import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.iot.service.v1.IotActionServiceV1
import net.softbell.bsh.service.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 액션 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/iot/action")
class IotActionRestV1 {
    @Autowired lateinit var responseService: ResponseService
    @Autowired lateinit var iotActionService: IotActionServiceV1

    @PostMapping("/exec/{id}")
    fun execNodeAction(auth: Authentication, @PathVariable("id") actionId: Long): ResultDto? {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = iotActionService!!.execAction(actionId, auth)

        // Return
        return if (isSuccess) responseService.getSuccessResult() else responseService!!.getFailResult(-10, "해당하는 아이템이 없음")
    }
}