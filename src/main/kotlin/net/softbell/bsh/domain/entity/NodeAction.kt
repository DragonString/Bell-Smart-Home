package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 엔티티
 */
@Entity
@Table(name = "node_action")
@NamedQuery(name = "NodeAction.findAll", query = "SELECT n FROM NodeAction n")
class NodeAction(
        @Column(nullable = false, length = 50)
        var description: String,

        @Column(name = "enable_status", nullable = false)
        var enableStatus: EnableStatusRule,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        var member: Member? = null,

        @OneToMany(mappedBy = "nodeAction")
        var nodeActionItems: MutableList<NodeActionItem> = ArrayList(),

        @OneToMany(mappedBy = "nodeAction")
        var nodeReservActions: MutableList<NodeReservAction> = ArrayList(),

        @OneToMany(mappedBy = "nodeAction")
        var nodeTriggerActions: MutableList<NodeTriggerAction> = ArrayList()
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id", unique = true, nullable = false)
    var actionId: Long = 0

    fun addNodeActionItem(nodeActionItem: NodeActionItem): NodeActionItem? {
        nodeActionItems.add(nodeActionItem)
        nodeActionItem.nodeAction = this
        return nodeActionItem
    }

//    fun removeNodeActionItem(nodeActionItem: NodeActionItem): NodeActionItem? {
//        nodeActionItems.remove(nodeActionItem)
//        nodeActionItem.nodeAction = null
//        return nodeActionItem
//    }

    fun addNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction? {
        nodeReservActions.add(nodeReservAction)
        nodeReservAction.nodeAction = this
        return nodeReservAction
    }

//    fun removeNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction? {
//        nodeReservActions.remove(nodeReservAction)
//        nodeReservAction.nodeAction = null
//        return nodeReservAction
//    }

    fun addNodeTriggerActions(nodeTriggerAction: NodeTriggerAction): NodeTriggerAction? {
        nodeTriggerActions.add(nodeTriggerAction)
        nodeTriggerAction.nodeAction = this
        return nodeTriggerAction
    }

//    fun removeNodeTriggerActions(nodeTriggerAction: NodeTriggerAction): NodeTriggerAction? {
//        nodeTriggerActions.remove(nodeTriggerAction)
//        nodeTriggerAction.nodeAction = null
//        return nodeTriggerAction
//    }

    companion object {
        private const val serialVersionUID = 1L
    }
}