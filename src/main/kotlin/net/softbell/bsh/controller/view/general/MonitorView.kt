package net.softbell.bsh.controller.view.general

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터 뷰 컨트롤러
 */
@Controller
@RequestMapping("/monitor")
class MonitorView constructor() {
    // Global Field
    private val G_BASE_PATH: String = "services/general"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"

    @Autowired lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired lateinit var aiotNodeService: IotNodeServiceV1
    @Autowired lateinit var centerService: CenterService

    @GetMapping
    fun dispIndex(model: Model, auth: Authentication,
                  @RequestParam(value = "page", required = false, defaultValue = "1") intPage: Int,
                  @RequestParam(value = "count", required = false, defaultValue = "100") intCount: Int): String {
        // Exception
        if (centerService.getSetting().getIotMonitor() !== 1) return G_INDEX_REDIRECT_URL

        // Field
        val listNode: List<Node?>?

        // Init
        listNode = iotNodeService!!.getAllNodes(auth, GroupRole.MONITOR)

        // Process
        model.addAttribute("listCardNodes", viewDtoConverterService!!.convMonitorSummaryCards(listNode))

        // Return
        return G_BASE_PATH + "/Monitor"
    }
}