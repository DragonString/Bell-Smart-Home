package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 엔티티
 */
@Entity
@Table(name = "node_group")
@NamedQuery(name = "NodeGroup.findAll", query = "SELECT n FROM NodeGroup n")
class NodeGroup : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_group_id", unique = true, nullable = false)
    var nodeGroupId: Long? = null

    @Column(name = "enable_status", nullable = false)
    var enableStatus: EnableStatusRule? = null

    @Column(nullable = false, length = 50)
    var name: String? = null

    @OneToMany(mappedBy = "nodeGroup")
    var groupPermissions: MutableList<GroupPermission>? = null

    @OneToMany(mappedBy = "nodeGroup")
    var nodeGroupItems: MutableList<NodeGroupItem>? = null


    fun addGroupPermission(groupPermission: GroupPermission): GroupPermission? {
        groupPermissions?.add(groupPermission)
        groupPermission.nodeGroup = this
        return groupPermission
    }

    fun removeGroupPermission(groupPermission: GroupPermission): GroupPermission? {
        groupPermissions?.remove(groupPermission)
        groupPermission.nodeGroup = null
        return groupPermission
    }

    fun addNodeGroupItem(nodeGroupItem: NodeGroupItem): NodeGroupItem? {
        nodeGroupItems?.add(nodeGroupItem)
        nodeGroupItem.nodeGroup = this
        return nodeGroupItem
    }

    fun removeNodeGroupItem(nodeGroupItem: NodeGroupItem): NodeGroupItem? {
        nodeGroupItems?.remove(nodeGroupItem)
        nodeGroupItem.nodeGroup = null
        return nodeGroupItem
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}