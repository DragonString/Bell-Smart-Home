package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.NodeGroup
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 정보 카드 DTO
 */
class MemberGroupPermissionCardDto(entities: List<NodeGroup?>) {
    var listNodes: MutableList<MemberGroupNode>
    var listPermissions: MutableList<MemberGroupPermission>

    inner class MemberGroupNode(entity: NodeGroup) {
        var gid: Long?
        var name: String?

        init {
            // Convert
            gid = entity.nodeGroupId
            name = entity.name
        }
    }

    inner class MemberGroupPermission(role: GroupRole) {
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

            // Init
            listNodes = ArrayList()
            listPermissions = ArrayList()

            // Convert
            for (entity in entities)
                if (entity != null)
                    listNodes.add(MemberGroupNode(entity))
            for (role in GroupRole.values())
                listPermissions.add(MemberGroupPermission(role))
        }
    }
}