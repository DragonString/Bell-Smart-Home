package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.TriggerStatusRule
import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 액션 엔티티
 */
@Entity
@Table(name = "node_trigger_action")
@NamedQuery(name = "NodeTriggerAction.findAll", query = "SELECT n FROM NodeTriggerAction n")
class NodeTriggerAction : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trigger_action_id", unique = true, nullable = false)
    var triggerActionId: Long? = null

    @Column(name = "trigger_status", nullable = false)
    var triggerStatus: TriggerStatusRule? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trigger_id", nullable = false)
    var nodeTrigger: NodeTrigger? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", nullable = false)
    var nodeAction: NodeAction? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}