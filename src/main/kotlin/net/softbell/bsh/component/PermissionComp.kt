package net.softbell.bsh.component

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.*
import net.softbell.bsh.domain.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 권한 관련 컴포넌트
 */
@Component
class PermissionComp {
    // Global Field
    @Autowired lateinit var groupPermissionRepo: GroupPermissionRepo
    @Autowired lateinit var memberGroupRepo: MemberGroupRepo
    @Autowired lateinit var memberGroupItemRepo: MemberGroupItemRepo
    @Autowired lateinit var nodeGroupRepo: NodeGroupRepo
    @Autowired lateinit var nodeGroupItemRepo: NodeGroupItemRepo

    // 활성화된 사용자 그룹 반환
    fun getEnableMemberGroup(): List<MemberGroup?>? {
        // Field
        val listMemberGroup: List<MemberGroup?>?

        // Init
        listMemberGroup = memberGroupRepo.findByEnableStatus(EnableStatusRule.ENABLE)

        // Return
        return listMemberGroup
    }

    // 사용자가 포함된 활성화된 사용자 그룹 반환
    fun getEnableMemberGroup(member: Member?): List<MemberGroup?>? {
        // Field
        val listMemberGroupItem: List<MemberGroupItem?>?
        val listMemberGroup: List<MemberGroup?>?

        // Init
        listMemberGroupItem = memberGroupItemRepo.findByMember(member)
        listMemberGroup = memberGroupRepo.findByMemberGroupItemsInAndEnableStatus(listMemberGroupItem, EnableStatusRule.ENABLE)

        // Return
        return listMemberGroup
    }

    // 권한이 있는 사용자 그룹 반환
    fun getPrivilegeMemberGroup(listGroupPermission: List<GroupPermission?>?): List<MemberGroup?>? {
        // Return
        return memberGroupRepo.findByGroupPermissionsIn(listGroupPermission)
    }

    // 권한이 있는 사용자 그룹 반환
    fun getPrivilegeMemberGroup(listMemberGroup: List<MemberGroup>, listGroupPermission: List<GroupPermission?>?): List<MemberGroup?>? {
        // Field
        val listMemberGroupId: MutableList<Long?>

        // Init
        listMemberGroupId = ArrayList()

        // Load
        for (entity in listMemberGroup) listMemberGroupId.add(entity.memberGroupId)

        // Return
        return memberGroupRepo.findByMemberGroupIdInAndGroupPermissionsIn(listMemberGroupId, listGroupPermission)
    }

    // 활성화된 노드 그룹 반환
    fun getEnableNodeGroup(): List<NodeGroup>? {
        // Field
        val listNodeGroup: List<NodeGroup>?

        // Init
        listNodeGroup = nodeGroupRepo.findByEnableStatus(EnableStatusRule.ENABLE)

        // Return
        return listNodeGroup
    }

    // 노드가 포함된 활성화된 노드 그룹 반환
    fun getEnableNodeGroup(node: Node?): List<NodeGroup?>? {
        // Field
        val listNodeGroupItem: List<NodeGroupItem?>?
        val listNodeGroup: List<NodeGroup?>?

        // Init
        listNodeGroupItem = nodeGroupItemRepo.findByNode(node)
        listNodeGroup = nodeGroupRepo.findByNodeGroupItemsInAndEnableStatus(listNodeGroupItem, EnableStatusRule.ENABLE)

        // Return
        return listNodeGroup
    }

    // 권한이 있는 사용자 그룹 반환
    fun getPrivilegeNodeGroup(listNodeGroup: List<NodeGroup>, listGroupPermission: List<GroupPermission?>?): List<NodeGroup?>? {
        // Field
        val listNodeGroupId: MutableList<Long?>

        // Init
        listNodeGroupId = ArrayList()

        // Load
        for (entity in listNodeGroup) listNodeGroupId.add(entity.nodeGroupId)

        // Return
        return nodeGroupRepo.findByNodeGroupIdInAndGroupPermissionsIn(listNodeGroupId, listGroupPermission)
    }


    // 사용자 그룹으로 특정 권한이 포함된 그룹 권한 반환
    fun getMemberGroupPermission(role: GroupRole?, listMemberGroup: List<MemberGroup?>?): List<GroupPermission?>? {
        // Field
        val listGroupPermission: List<GroupPermission?>?

        // Init
        listGroupPermission = groupPermissionRepo.findByGroupPermissionAndMemberGroupIn(role, listMemberGroup)

        // Return
        return listGroupPermission
    }

    // 노드 그룹으로 특정 권한이 포함된 그룹 권한 반환
    fun getNodeGroupPermission(role: GroupRole?, listNodeGroup: List<NodeGroup?>?): List<GroupPermission?>? {
        // Field
        val listGroupPermission: List<GroupPermission?>?

        // Init
        listGroupPermission = groupPermissionRepo.findByGroupPermissionAndNodeGroupIn(role, listNodeGroup)

        // Return
        return listGroupPermission
    }

    // 노드 그룹과 사용자 그룹으로 특정 권한이 포함된 그룹 권한 반환
    fun getGroupPermission(role: GroupRole?, listMemberGroup: List<MemberGroup?>?, listNodeGroup: List<NodeGroup?>?): List<GroupPermission?>? {
        // Field
        val listGroupPermission: List<GroupPermission?>?

        // Init
        listGroupPermission = groupPermissionRepo.findByGroupPermissionAndMemberGroupInAndNodeGroupIn(role, listMemberGroup, listNodeGroup)

        // Return
        return listGroupPermission
    }
}