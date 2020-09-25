package net.softbell.bsh.controller.rest.api.v1

import lombok.RequiredArgsConstructor
import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.iot.service.v1.IotReservServiceV1
import net.softbell.bsh.service.ResponseService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 관련 REST API 컨트롤러 V1
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/reserv")
class ReservRestV1 constructor() {
    // Global Field
    private val responseService: ResponseService? = null
    private val iotReservService: IotReservServiceV1? = null
    @PostMapping("/status/{id}")
    fun setTriggerStatus(auth: Authentication?, @PathVariable("id") id: Long, @RequestParam("status") status: Boolean): ResultDto? {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = iotReservService!!.setTriggerEnableStatus(auth, id, status)

        // Return
        if (isSuccess) return responseService.getSuccessResult() else return responseService!!.getFailResult(-10, "해당하는 아이템이 없음")
    }
}