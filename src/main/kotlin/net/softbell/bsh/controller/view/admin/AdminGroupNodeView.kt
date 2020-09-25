package net.softbell.bsh.controller.view.admin

import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroup
import net.softbell.bsh.domain.entity.NodeGroupItem
import net.softbell.bsh.dto.request.NodeGroupDto
import net.softbell.bsh.dto.request.NodeGroupPermissionDto
import net.softbell.bsh.dto.view.admin.group.NodeGroupInfoCardDto
import net.softbell.bsh.dto.view.admin.group.NodeGroupPermissionCardDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.PermissionService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 노드 그룹 관리 뷰 컨트롤러
 */
@Controller
@RequestMapping("/admin/group/node")
class AdminGroupNodeView constructor() {
    // Global Field
    private val G_BASE_PATH: String = "services/admin/group"
    private val G_BASE_REDIRECT_URL: String = "redirect:/admin/group/node"
    private val G_LOGOUT_REDIRECT_URL: String = "redirect:/logout"

    @Autowired lateinit var viewDtoConverterService: ViewDtoConverterService
    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var permissionService: PermissionService
    @Autowired lateinit var iotNodeService: IotNodeServiceV1

    @GetMapping
    fun dispGroupNode(model: Model): String {
        // Load
        model.addAttribute("listCardGroups", viewDtoConverterService!!.convNodeGroupSummaryCards(permissionService.getAllNodeGroup()))

        // Return
        return G_BASE_PATH + "/NodeGroup"
    }

    @GetMapping("/create")
    fun dispGroupCreate(model: Model): String {
        // Field

        // Init
        model.addAttribute("listCardNodes", viewDtoConverterService!!.convGroupNodeCardItems(iotNodeService.getAllNodes()))

        // Return
        return G_BASE_PATH + "/NodeGroupCreate"
    }

    @GetMapping("/modify/{gid}")
    fun dispGroupModify(model: Model, @PathVariable("gid") gid: Long): String {
        // Field
        val listNode: MutableList<Node?>?
        val nodeGroup: NodeGroup?

        // Init
        listNode = iotNodeService.getAllNodes()
        nodeGroup = permissionService!!.getNodeGroup(gid)

        // Process
        for (entity: NodeGroupItem in nodeGroup.getNodeGroupItems()) listNode!!.remove(entity.getNode())

        // View
        model.addAttribute("cardGroup", NodeGroupInfoCardDto(permissionService.getNodeGroup(gid)))
        model.addAttribute("listCardNodes", viewDtoConverterService!!.convGroupNodeCardItems(listNode))

        // Return
        return G_BASE_PATH + "/NodeGroupModify"
    }

    @GetMapping("/{gid}")
    fun dispGroup(model: Model, @PathVariable("gid") gid: Long): String {
        // Field

        // Init
        model.addAttribute("cardPermission", NodeGroupPermissionCardDto(permissionService.getAllMemberGroup()))
        model.addAttribute("cardGroup", NodeGroupInfoCardDto(permissionService!!.getNodeGroup(gid)))

        // Return
        return G_BASE_PATH + "/NodeGroupInfo"
    }

    @PostMapping("/create")
    fun procGroupCreate(auth: Authentication?, nodeGroupDto: NodeGroupDto): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.createNodeGroup(nodeGroupDto)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?err"
    }

    @PostMapping("/modify/{gid}")
    fun procGroupModify(auth: Authentication?, @PathVariable("gid") gid: Long, nodeGroupDto: NodeGroupDto): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.modifyNodeGroup(gid, nodeGroupDto)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/" + gid else return G_BASE_REDIRECT_URL + "/" + gid + "?err"
    }

    @PostMapping("/enable")
    fun procGroupEnable(auth: Authentication?, @RequestParam("gid") listGid: List<Long>): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.enableNodeGroup(listGid)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?err"
    }

    @PostMapping("/disable")
    fun procGroupDisable(auth: Authentication?, @RequestParam("gid") listGid: List<Long>): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.disableNodeGroup(listGid)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?err"
    }

    @PostMapping("/delete")
    fun procGroupDelete(auth: Authentication?, @RequestParam("gid") listGid: List<Long>): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.deleteNodeGroup(listGid)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?err"
    }

    @PostMapping("/permission/add/{gid}")
    fun addPermission(@PathVariable("gid") gid: Long, nodeGroupPermissionDto: NodeGroupPermissionDto): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.addNodePermission(gid, nodeGroupPermissionDto)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/" + gid else return G_BASE_REDIRECT_URL + "/" + gid + "?err"
    }

    @PostMapping("/permission/delete/{gid}")
    fun deletePermission(@PathVariable("gid") gid: Long, @RequestParam("pid") pid: Long): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.deleteGroupPermission(pid)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/" + gid else return G_BASE_REDIRECT_URL + "/" + gid + "?err"
    }
}