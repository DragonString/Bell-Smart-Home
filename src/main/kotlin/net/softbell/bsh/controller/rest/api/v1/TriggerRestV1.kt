package net.softbell.bsh.controller.rest.api.v1

import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.iot.service.v1.IotTriggerServiceV1
import net.softbell.bsh.service.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 관련 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/trigger")
class TriggerRestV1 constructor() {
    // Global Field
    @Autowired lateinit var responseService: ResponseService
    @Autowired lateinit var iotTriggerService: IotTriggerServiceV1

    @PostMapping("/status/{id}")
    fun setTriggerStatus(auth: Authentication?, @PathVariable("id") id: Long, @RequestParam("status") status: Boolean): ResultDto? {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = iotTriggerService!!.setTriggerEnableStatus(auth, id, status)

        // Return
        if (isSuccess) return responseService.getSuccessResult() else return responseService!!.getFailResult(-10, "해당하는 아이템이 없음")
    }
}