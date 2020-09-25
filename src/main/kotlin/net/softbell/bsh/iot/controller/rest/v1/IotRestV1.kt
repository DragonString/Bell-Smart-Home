package net.softbell.bsh.iot.controller.rest.v1

import lombok.AllArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT REST API 컨트롤러 V1
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/iot")
class IotRestV1 {
    @GetMapping("/send")
    fun procSend(): String {
        // Field


        // Finish
        return "Success"
    }
}