package net.softbell.bsh.iot.controller.rest.v1

import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.iot.component.v1.IotAuthCompV1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 인증 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/iot/auth")
class IotAuthRestV1 {
    @Autowired private lateinit var iotAuthComp: IotAuthCompV1

    @GetMapping("/token/check")
    fun checkTokenAvailable(@RequestParam("token") token: String): ResultDto {
        // Init
        val message = ResultDto()

        // Check
        val isAvailable = iotAuthComp.isTokenAvailable(token)

        // Message
        if (isAvailable)
            message.message = "valid"
        else
            message.message = "invalid"

        // Return
        return message
    }
}