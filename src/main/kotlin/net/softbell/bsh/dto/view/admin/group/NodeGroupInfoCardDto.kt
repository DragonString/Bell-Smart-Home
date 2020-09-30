package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroup
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 정보 카드 DTO
 */
class NodeGroupInfoCardDto(entity: NodeGroup?) {
    var gid: Long?
    var enableStatus: EnableStatusRule?
    var name: String?
    var listNodes: MutableList<MemberGroupInfoCardNode>
    var listPermissions: MutableList<MemberGroupInfoCardPermission>

    inner class MemberGroupInfoCardNode(entity: Node?) {
        var nodeId: Long?
        var uid: String?
        var name: String?
        var alias: String?
        var version: String?

        init {
            // Convert
            nodeId = entity!!.nodeId
            uid = entity.uid
            this.name = entity.nodeName
            alias = entity.alias
            version = entity.version
        }
    }

    inner class MemberGroupInfoCardPermission(entity: GroupPermission) {
        var pid: Long?
        var gid: Long?
        var name: String?
        var permission: GroupRole?
        var assignDate: Date?

        init {
            pid = entity.groupPermissionId
            this.gid = entity.memberGroup!!.memberGroupId
            this.name = entity.memberGroup!!.name
            permission = entity.groupPermission
            assignDate = entity.assignDate
        }
    }

    init {
        // Exception
        entity.let {
            // Init
            listNodes = ArrayList()
            listPermissions = ArrayList()

            // Convert
            gid = entity!!.nodeGroupId
            enableStatus = entity.enableStatus
            name = entity.name
            for (child in entity.nodeGroupItems!!) listNodes.add(MemberGroupInfoCardNode(child.node))
            for (child in entity.groupPermissions!!) listPermissions.add(MemberGroupInfoCardPermission(child))
        }
    }
}