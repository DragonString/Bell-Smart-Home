package net.softbell.bsh.iot.controller.rest.v1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/iot")
class IotRestV1 {
    @GetMapping("/send")
    fun procSend(): String {
        // Init

        // Finish
        return "Success"
    }
}