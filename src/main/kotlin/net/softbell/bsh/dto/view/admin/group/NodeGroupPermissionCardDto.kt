package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.MemberGroup

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 그룹 정보 카드 DTO
 */
class NodeGroupPermissionCardDto(entities: List<MemberGroup>) {
    val listMembers: MutableList<NodeGroupNode> = ArrayList()
    val listPermissions: MutableList<NodeGroupPermission> = ArrayList()

    inner class NodeGroupNode(entity: MemberGroup) {
        val gid: Long = entity.memberGroupId
        val name: String = entity.name
    }

    inner class NodeGroupPermission(role: GroupRole) {
        val pid: Int = role.code
        val name: String = role.value
    }

    init {
        // Convert
        for (entity in entities)
            listMembers.add(NodeGroupNode(entity))
        for (role in GroupRole.values())
            listPermissions.add(NodeGroupPermission(role))
    }
}