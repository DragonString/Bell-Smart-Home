package net.softbell.bsh.iot.controller.rest.v1

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.PermissionService
import net.softbell.bsh.service.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 제어 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/iot/control")
class IotControlRestV1 {
    @Autowired private lateinit var responseService: ResponseService
    @Autowired private lateinit var iotNodeService: IotNodeServiceV1
    @Autowired private lateinit var permissionService: PermissionService

    @PostMapping("/item/set/{id}")
    fun setNodeItemValue(@AuthenticationPrincipal member: Member,
                         @PathVariable("id") id: Long, @RequestParam("value") value: Short): ResultDto {
        // Init
        var isSuccess = false
        val nodeItem = iotNodeService.getNodeItem(id)

        // Process
        if (nodeItem != null)
            if (permissionService.isPrivilege(GroupRole.MANUAL_CONTROL, member, nodeItem.node))
                isSuccess = iotNodeService.setItemValue(nodeItem, value.toDouble())

        // Return
        return if (isSuccess)
            responseService.getSuccessResult()
        else
            responseService.getFailResult(-10, "해당하는 아이템이 없음")
    }

    @PostMapping("/node/restart/{id}")
    fun restartNode(@AuthenticationPrincipal member: Member,
                    @PathVariable("id") id: Long): ResultDto {
        // Init
        var isSuccess = false
        val node = iotNodeService.getNode(id)

        // Process
        if (node != null)
            if (permissionService.isPrivilege(GroupRole.MANUAL_CONTROL, member, node))
                isSuccess = iotNodeService.restartNode(node)

        // Return
        return if (isSuccess)
            responseService.getSuccessResult()
        else
            responseService.getFailResult(-10, "해당하는 아이템이 없음")
    }
}