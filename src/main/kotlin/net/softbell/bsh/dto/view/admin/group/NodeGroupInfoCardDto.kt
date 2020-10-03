package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.entity.NodeGroup
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 그룹 정보 카드 DTO
 */
class NodeGroupInfoCardDto(entity: NodeGroup) {
    val gid: Long = entity.nodeGroupId
    val enableStatus: EnableStatusRule = entity.enableStatus
    val name: String = entity.name
    val listNodes: MutableList<MemberGroupInfoCardNode> = ArrayList()
    val listPermissions: MutableList<MemberGroupInfoCardPermission> = ArrayList()

    inner class MemberGroupInfoCardNode(entity: Node) {
        val nodeId: Long = entity.nodeId
        val uid: String = entity.uid
        val name: String = entity.nodeName
        val alias: String = entity.alias
        val version: String = entity.version
    }

    inner class MemberGroupInfoCardPermission(entity: GroupPermission) {
        val pid: Long = entity.groupPermissionId
        val gid: Long = entity.memberGroup.memberGroupId
        val name: String = entity.memberGroup.name
        val permission: GroupRole = entity.groupPermission
        val assignDate: Date = entity.assignDate
    }

    init {
        // Convert
        for (child in entity.nodeGroupItems)
            listNodes.add(MemberGroupInfoCardNode(child.node))
        for (child in entity.groupPermissions)
            listPermissions.add(MemberGroupInfoCardPermission(child))
    }
}