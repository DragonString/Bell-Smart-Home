package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.TriggerLastStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 엔티티
 */
@Entity
@Table(name = "node_trigger")
@NamedQuery(name = "NodeTrigger.findAll", query = "SELECT n FROM NodeTrigger n")
class NodeTrigger : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trigger_id", unique = true, nullable = false)
    var triggerId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_date")
    var changeDate: Date? = null

    @Column(nullable = false, length = 100)
    var description: String? = null

    @Column(name = "enable_status", nullable = false)
    var enableStatus: EnableStatusRule? = null

    @Column(nullable = false, length = 100)
    var expression: String? = null

    @Column(name = "last_status", nullable = false)
    var lastStatus: TriggerLastStatusRule? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null

    @OneToMany(mappedBy = "nodeTrigger")
    var nodeTriggerActions: MutableList<NodeTriggerAction>? = null


    fun addNodeTriggerAction(nodeTriggerAction: NodeTriggerAction): NodeTriggerAction? {
        nodeTriggerActions?.add(nodeTriggerAction)
        nodeTriggerAction.nodeTrigger = this
        return nodeTriggerAction
    }

    fun removeNodeTriggerAction(nodeTriggerAction: NodeTriggerAction): NodeTriggerAction? {
        nodeTriggerActions?.remove(nodeTriggerAction)
        nodeTriggerAction.nodeTrigger = null
        return nodeTriggerAction
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}