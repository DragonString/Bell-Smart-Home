package net.softbell.bsh.controller.view

import lombok.RequiredArgsConstructor
import net.softbell.bsh.service.DashboardService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 대시보드 뷰 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
class DashboardView constructor() {
    // Global Field
    private val dashboardService: DashboardService? = null
    @GetMapping
    fun dispIndex(model: Model): String {
        // Field

        // Init

        // Process
        model.addAttribute("listCardHumidityWarns", dashboardService.getHumidityWarn())
        model.addAttribute("listCardTemperatureWarns", dashboardService.getTemperatureWarn())

        // Return
        return "services/dashboard/Dashboard"
    }
}