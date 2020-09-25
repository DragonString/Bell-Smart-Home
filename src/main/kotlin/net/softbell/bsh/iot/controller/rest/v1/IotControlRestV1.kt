package net.softbell.bsh.iot.controller.rest.v1;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.PermissionService;
import net.softbell.bsh.service.ResponseService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 제어 REST API 컨트롤러 V1
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/iot/control")
public class IotControlRestV1
{
    private final ResponseService responseService;
    private final IotNodeServiceV1 iotNodeService;
    private final PermissionService permissionService;
	
	@PostMapping("/item/set/{id}")
	public ResultDto setNodeItemValue(Authentication auth, 
			@PathVariable("id")long id, @RequestParam("value")short value)
	{
		// Field
		boolean isSuccess;
		NodeItem nodeItem;
		
		// Init
		isSuccess = false;
		nodeItem = iotNodeService.getNodeItem(id);
		
		// Process
		if (nodeItem != null)
			if (permissionService.isPrivilege(GroupRole.MANUAL_CONTROL, auth, nodeItem.getNode()))
				isSuccess = iotNodeService.setItemValue(nodeItem, value);
		
		// Return
		if (isSuccess)
			return responseService.getSuccessResult();
		else
			return responseService.getFailResult(-10, "해당하는 아이템이 없음");
	}
	
	@PostMapping("/node/restart/{id}")
	public ResultDto restartNode(Authentication auth, 
			@PathVariable("id")long id)
	{
		// Field
		boolean isSuccess;
		Node node;
		
		// Init
		isSuccess = false;
		node = iotNodeService.getNode(id);
		
		// Process
		if (node != null)
			if (permissionService.isPrivilege(GroupRole.MANUAL_CONTROL, auth, node))
				isSuccess = iotNodeService.restartNode(node);
		
		// Return
		if (isSuccess)
			return responseService.getSuccessResult();
		else
			return responseService.getFailResult(-10, "해당하는 아이템이 없음");
	}
}
