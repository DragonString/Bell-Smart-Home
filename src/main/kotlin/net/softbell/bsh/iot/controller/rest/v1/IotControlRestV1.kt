package net.softbell.bsh.iot.controller.rest.v1

import lombok.AllArgsConstructor
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.PermissionService
import net.softbell.bsh.service.ResponseService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 제어 REST API 컨트롤러 V1
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/iot/control")
class IotControlRestV1 {
    private val responseService: ResponseService? = null
    private val iotNodeService: IotNodeServiceV1? = null
    private val permissionService: PermissionService? = null
    @PostMapping("/item/set/{id}")
    fun setNodeItemValue(auth: Authentication,
                         @PathVariable("id") id: Long, @RequestParam("value") value: Short): ResultDto? {
        // Field
        var isSuccess: Boolean
        val nodeItem: NodeItem?

        // Init
        isSuccess = false
        nodeItem = iotNodeService!!.getNodeItem(id)

        // Process
        if (nodeItem != null) if (permissionService!!.isPrivilege(GroupRole.MANUAL_CONTROL, auth, nodeItem.getNode())) isSuccess = iotNodeService.setItemValue(nodeItem, value.toDouble())

        // Return
        return if (isSuccess) responseService.getSuccessResult() else responseService!!.getFailResult(-10, "해당하는 아이템이 없음")
    }

    @PostMapping("/node/restart/{id}")
    fun restartNode(auth: Authentication,
                    @PathVariable("id") id: Long): ResultDto? {
        // Field
        var isSuccess: Boolean
        val node: Node?

        // Init
        isSuccess = false
        node = iotNodeService!!.getNode(id)

        // Process
        if (node != null) if (permissionService!!.isPrivilege(GroupRole.MANUAL_CONTROL, auth, node)) isSuccess = iotNodeService.restartNode(node)

        // Return
        return if (isSuccess) responseService.getSuccessResult() else responseService!!.getFailResult(-10, "해당하는 아이템이 없음")
    }
}