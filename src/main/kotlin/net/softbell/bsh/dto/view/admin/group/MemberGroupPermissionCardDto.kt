package net.softbell.bsh.dto.view.admin.group

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.NodeGroup
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 정보 카드 DTO
 */
@Getter
@Setter
class MemberGroupPermissionCardDto(entities: List<NodeGroup?>?) {
    private val listNodes: MutableList<MemberGroupNode>
    private val listPermissions: MutableList<MemberGroupPermission>

    @Getter
    @Setter
    inner class MemberGroupNode(entity: NodeGroup?) {
        private val gid: Long
        private val name: String

        init {
            // Convert
            gid = entity.getNodeGroupId()
            name = entity.getName()
        }
    }

    @Getter
    @Setter
    inner class MemberGroupPermission(role: GroupRole) {
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
        listNodes = ArrayList()
        listPermissions = ArrayList()

        // Convert
        for (entity in entities!!) listNodes.add(MemberGroupNode(entity))
        for (role in GroupRole.values()) listPermissions.add(MemberGroupPermission(role))
    }
}