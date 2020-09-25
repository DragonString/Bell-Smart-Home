package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.MemberGroup

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 정보 카드 DTO
 */
class NodeGroupPermissionCardDto(entities: List<MemberGroup?>?) {
    private val listMembers: MutableList<NodeGroupNode>
    private val listPermissions: MutableList<NodeGroupPermission>

    inner class NodeGroupNode(entity: MemberGroup?) {
        private val gid: Long
        private val name: String

        init {
            // Convert
            gid = entity.getMemberGroupId()
            name = entity.getName()
        }
    }

    inner class NodeGroupPermission(role: GroupRole) {
        private val pid: Int
        private val name: String

        init {
            pid = role.getCode()
            name = role.getValue()
        }
    }

    init {
        // Exception
        if (entities == null || entities.isEmpty()) return

        // Init
        listMembers = ArrayList()
        listPermissions = ArrayList()

        // Convert
        for (entity in entities!!) listMembers.add(NodeGroupNode(entity))
        for (role in GroupRole.values()) listPermissions.add(NodeGroupPermission(role))
    }
}