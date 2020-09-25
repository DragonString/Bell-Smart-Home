package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_action")
@NamedQuery(name = "NodeAction.findAll", query = "SELECT n FROM NodeAction n")
class NodeAction : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id", unique = true, nullable = false)
    private val actionId: Long? = null

    @Column(nullable = false, length = 50)
    private val description: String? = null

    @Column(name = "enable_status", nullable = false)
    private val enableStatus: EnableStatusRule? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private val member: Member? = null

    @OneToMany(mappedBy = "nodeAction")
    private val nodeActionItems: List<NodeActionItem>? = null

    @OneToMany(mappedBy = "nodeAction")
    private val nodeReservActions: List<NodeReservAction>? = null

    @OneToMany(mappedBy = "nodeAction")
    private val nodeTriggerActions: List<NodeTriggerAction>? = null
    fun addNodeActionItem(nodeActionItem: NodeActionItem): NodeActionItem {
        getNodeActionItems().add(nodeActionItem)
        nodeActionItem.setNodeAction(this)
        return nodeActionItem
    }

    fun removeNodeActionItem(nodeActionItem: NodeActionItem): NodeActionItem {
        getNodeActionItems().remove(nodeActionItem)
        nodeActionItem.setNodeAction(null)
        return nodeActionItem
    }

    fun addNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction {
        getNodeReservActions().add(nodeReservAction)
        nodeReservAction.setNodeAction(this)
        return nodeReservAction
    }

    fun removeNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction {
        getNodeReservActions().remove(nodeReservAction)
        nodeReservAction.setNodeAction(null)
        return nodeReservAction
    }

    fun addNodeTriggerActions(nodeTriggerActions: NodeTriggerAction): NodeTriggerAction {
        getNodeTriggerActions().add(nodeTriggerActions)
        nodeTriggerActions.setNodeAction(this)
        return nodeTriggerActions
    }

    fun removeNodeTriggerActions(nodeTriggerActions: NodeTriggerAction): NodeTriggerAction {
        getNodeTriggerActions().remove(nodeTriggerActions)
        nodeTriggerActions.setNodeAction(null)
        return nodeTriggerActions
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}