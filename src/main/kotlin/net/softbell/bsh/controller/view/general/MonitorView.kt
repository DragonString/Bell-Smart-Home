package net.softbell.bsh.controller.view.general

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author : Bell(bell@softbell.net)
 * @description : 모니터 뷰 컨트롤러
 */
@Controller
@RequestMapping("/monitor")
class MonitorView {
    // Global Field
    private val G_BASE_PATH: String = "services/general"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"

    @Autowired private lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired private lateinit var iotNodeService: IotNodeServiceV1
    @Autowired private lateinit var centerService: CenterService

    @GetMapping
    fun dispIndex(model: Model, @AuthenticationPrincipal member: Member,
                  @RequestParam(value = "page", required = false, defaultValue = "1") intPage: Int,
                  @RequestParam(value = "count", required = false, defaultValue = "100") intCount: Int): String {
        // Exception
        if (centerService.setting.iotMonitor != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val listNode = iotNodeService.getPrivilegesNodes(member, GroupRole.MONITOR)

        // Process
        model.addAttribute("listCardNodes", viewDtoConverterService.convMonitorSummaryCards(listNode))

        // Return
        return "$G_BASE_PATH/Monitor"
    }
}