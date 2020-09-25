package net.softbell.bsh.dto.view.admin.group

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroup
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 정보 카드 DTO
 */
@Getter
@Setter
class NodeGroupInfoCardDto(entity: NodeGroup?) {
    private val gid: Long
    private val enableStatus: EnableStatusRule
    private val name: String
    private val listNodes: MutableList<MemberGroupInfoCardNode>
    private val listPermissions: MutableList<MemberGroupInfoCardPermission>

    @Getter
    @Setter
    inner class MemberGroupInfoCardNode(entity: Node) {
        private val nodeId: Long
        private val uid: String
        private val name: String
        private val alias: String
        private val version: String

        init {
            // Convert
            nodeId = entity.getNodeId()
            uid = entity.getUid()
            this.name = entity.getNodeName()
            alias = entity.getAlias()
            version = entity.getVersion()
        }
    }

    @Getter
    @Setter
    inner class MemberGroupInfoCardPermission(entity: GroupPermission) {
        private val pid: Long
        private val gid: Long
        private val name: String
        private val permission: GroupRole
        private val assignDate: Date

        init {
            pid = entity.getGroupPermissionId()
            this.gid = entity.getMemberGroup().getMemberGroupId()
            this.name = entity.getMemberGroup().getName()
            permission = entity.getGroupPermission()
            assignDate = entity.getAssignDate()
        }
    }

    init {
        // Exception
        if (entity == null) return

        // Init
        listNodes = ArrayList()
        listPermissions = ArrayList()

        // Convert
        gid = entity.getNodeGroupId()
        enableStatus = entity.getEnableStatus()
        name = entity.getName()
        for (child in entity.getNodeGroupItems()) listNodes.add(MemberGroupInfoCardNode(child.getNode()))
        for (child in entity.getGroupPermissions()) listPermissions.add(MemberGroupInfoCardPermission(child))
    }
}