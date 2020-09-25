package net.softbell.bsh.controller.view

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 테스트 페이지 뷰 컨트롤러
 */
@Controller
@RequestMapping("/test/")
class TestView constructor() {
    // Global Field
    @GetMapping("blank")
    fun dispPage(model: Model, @RequestParam(value = "value", required = false, defaultValue = "방울 소프트 네트워크 최고다!!") value: String?): String {
        model.addAttribute("headInfo", value)
        return "test/sbadmin/blank"
    }

    @GetMapping("node")
    fun dispNodeInfo(model: Model?): String {
//		model.addAttribute("headInfo", value);
        return "test/sbadmin/NodeList"
    }
}