package net.softbell.bsh.iot.controller.rest.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT REST API 컨트롤러 V1
 */
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