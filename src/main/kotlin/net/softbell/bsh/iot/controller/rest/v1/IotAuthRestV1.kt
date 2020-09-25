package net.softbell.bsh.iot.controller.rest.v1

import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.iot.component.v1.IotAuthCompV1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 인증 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/iot/auth")
class IotAuthRestV1 {
    @Autowired lateinit var iotAuthComp: IotAuthCompV1

    @GetMapping("/token/check")
    fun checkTokenAvailable(@RequestParam("token") token: String?): ResultDto {
        // Field
        val message: ResultDto
        val isAvailable: Boolean

        // Check
        isAvailable = iotAuthComp!!.isTokenAvailable(token)

        // Message
        message = if (isAvailable) builder().message("valid").build() else builder().message("invalid").build()

        // Return
        return message
    }
}