package net.softbell.bsh.controller.view.admin

import net.softbell.bsh.dto.request.CenterSettingDto
import net.softbell.bsh.dto.view.admin.CenterSettingSummaryCardDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.CenterService
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * @author : Bell(bell@softbell.net)
 * @description : 관리자 뷰 컨트롤러
 */
@Controller
@RequestMapping("/admin")
class AdminView {
    // Global Field
    private val G_BASE_PATH: String = "services/admin"
    private val G_BASE_REDIRECT_URL: String = "redirect:/admin"
    private val G_INDEX_REDIRECT_URL: String = "redirect:/"

    @Autowired private lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var iotNodeService: IotNodeServiceV1
    @Autowired private lateinit var centerService: CenterService

    @GetMapping("/member")
    fun dispMember(model: Model,
                   @RequestParam(value = "page", required = false, defaultValue = "1") intPage: Int,
                   @RequestParam(value = "count", required = false, defaultValue = "100") intCount: Int): String {
        // Init
        val pageMember = memberService.getMemberList(intPage, intCount)

        // Load
        model.addAttribute("listCardMembers", viewDtoConverterService.convMemberSummaryCards(pageMember.content))

        // Return
        return "$G_BASE_PATH/Member"
    }

    @GetMapping("/node")
    fun dispNode(model: Model,
                 @RequestParam(value = "page", required = false, defaultValue = "1") intPage: Int,
                 @RequestParam(value = "count", required = false, defaultValue = "100") intCount: Int): String {
        // Exception
        if (centerService.setting.iotNode != 1.toByte())
            return G_INDEX_REDIRECT_URL

        // Init
        val pageNode = iotNodeService.getAllNodes(intPage, intCount)

        // Process
        model.addAttribute("listCardNodes", viewDtoConverterService.convNodeManageSummaryCards(pageNode.content))

        // Return
        return "$G_BASE_PATH/Node"
    }

    @GetMapping("/center")
    fun dispCenterSetting(model: Model): String {
        // Process
        model.addAttribute("cardCenterSetting", CenterSettingSummaryCardDto(centerService.loadSetting()))
        model.addAttribute("cardCenterSettingDefault", CenterSettingSummaryCardDto(centerService.setting))

        // Return
        return "$G_BASE_PATH/CenterSetting"
    }

    @GetMapping("/center/modify")
    fun dispCenterSettingModify(model: Model): String {
        // Process
        model.addAttribute("cardCenterSetting", CenterSettingSummaryCardDto(centerService.loadSetting()))

        // Return
        return "$G_BASE_PATH/CenterSettingModify"
    }

    @PostMapping("/center/modify")
    fun modifyCenterSetting(model: Model, centerSettingDto: CenterSettingDto): String {
        // Init
        val isSuccess = centerService.modifyCenterSetting(centerSettingDto)

        // Return
        return if (isSuccess)
            "$G_BASE_REDIRECT_URL/center"
        else
            "$G_BASE_REDIRECT_URL/center/modify?err"
    }
}