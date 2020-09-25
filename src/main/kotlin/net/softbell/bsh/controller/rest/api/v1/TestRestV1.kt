package net.softbell.bsh.controller.rest.api.v1;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.component.PermissionComp;
import net.softbell.bsh.domain.GroupRole;
import net.softbell.bsh.domain.entity.GroupPermission;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberGroup;
import net.softbell.bsh.domain.entity.NodeGroup;
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1;
import net.softbell.bsh.service.MemberService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 테스트용 REST API 컨트롤러 V1
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/test")
public class TestRestV1
{
	// Global Field
	private final MemberService memberService;
	private final IotNodeServiceV1 nodeService;
	private final PermissionComp permissionComp;
    
    @GetMapping("/memberGroup")
    public String findMemberGroup(Authentication auth)
    {
    	Member member = memberService.getMember(auth.getName()); // 권한 체크할 사용자 불러옴
//    	Node node = nodeService.getNode(1);
    	List<MemberGroup> listMemberGroup = permissionComp.getEnableMemberGroup(member); // 권한 있는 사용자 그룹 가져옴
    	List<GroupPermission> listGroupPermission = permissionComp.getMemberGroupPermission(GroupRole.ACTION, listMemberGroup); // 액션 권한 가져옴
    	List<NodeGroup> listNodeGroup = permissionComp.getEnableNodeGroup();
    	
    	List<NodeGroup> listPNG = permissionComp.getPrivilegeNodeGroup(listNodeGroup, listGroupPermission);
    	
    	for (NodeGroup entity : listPNG)
    		System.out.println("권한있는 노드 그룹: " + entity.getName());
    	
    	return "Success";
    }
}

