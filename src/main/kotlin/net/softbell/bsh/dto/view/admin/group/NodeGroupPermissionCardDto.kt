package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.MemberGroup
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 정보 카드 DTO
 */
class NodeGroupPermissionCardDto(entities: List<MemberGroup?>) {
    var listMembers: MutableList<NodeGroupNode>
    var listPermissions: MutableList<NodeGroupPermission>

    inner class NodeGroupNode(entity: MemberGroup) {
        var gid: Long?
        var name: String?

        init {
            // Convert
            gid = entity.memberGroupId
            name = entity.name
        }
    }

    inner class NodeGroupPermission(role: GroupRole) {
        var pid: Int
        var name: String

        init {
            pid = role.code
            name = role.value
        }
    }

    init {
        // Exception
        entities.let {
            // Init
            listMembers = ArrayList()
            listPermissions = ArrayList()

            // Convert
            for (entity in entities)
                if (entity != null)
                    listMembers.add(NodeGroupNode(entity))
            for (role in GroupRole.values())
                listPermissions.add(NodeGroupPermission(role))
        }
    }
}