package net.softbell.bsh.controller.rest.api.v1

import net.softbell.bsh.component.PermissionComp
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberGroup
import net.softbell.bsh.domain.entity.NodeGroup
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 테스트용 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/test")
class TestRestV1 constructor() {
    // Global Field
    @Autowired lateinit var memberService: MemberService
    @Autowired lateinit var nodeService: IotNodeServiceV1
    @Autowired lateinit var permissionComp: PermissionComp

    @GetMapping("/memberGroup")
    fun findMemberGroup(auth: Authentication): String {
        val member: Member? = memberService!!.getMember(auth.getName()) // 권한 체크할 사용자 불러옴
        //    	Node node = nodeService.getNode(1);
        val listMemberGroup: List<MemberGroup?>? = permissionComp!!.getEnableMemberGroup(member) // 권한 있는 사용자 그룹 가져옴
        val listGroupPermission: List<GroupPermission?>? = permissionComp.getMemberGroupPermission(GroupRole.ACTION, listMemberGroup) // 액션 권한 가져옴
        val listNodeGroup: List<NodeGroup?>? = permissionComp.getEnableNodeGroup()
        val listPNG: List<NodeGroup?>? = permissionComp.getPrivilegeNodeGroup(listNodeGroup, listGroupPermission)
        for (entity: NodeGroup? in listPNG!!) System.out.println("권한있는 노드 그룹: " + entity.getName())
        return "Success"
    }
}