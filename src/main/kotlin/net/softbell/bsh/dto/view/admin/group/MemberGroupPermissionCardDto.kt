package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.NodeGroup

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 그룹 정보 카드 DTO
 */
class MemberGroupPermissionCardDto(entities: List<NodeGroup>) {
    val listNodes: MutableList<MemberGroupNode> = ArrayList()
    val listPermissions: MutableList<MemberGroupPermission> = ArrayList()

    inner class MemberGroupNode(entity: NodeGroup) {
        val gid: Long = entity.nodeGroupId
        val name: String = entity.name
    }

    inner class MemberGroupPermission(role: GroupRole) {
        val pid: Int = role.code
        val name: String = role.value
    }

    init {
        // Convert
        for (entity in entities)
            listNodes.add(MemberGroupNode(entity))
        for (role in GroupRole.values())
            listPermissions.add(MemberGroupPermission(role))
    }
}