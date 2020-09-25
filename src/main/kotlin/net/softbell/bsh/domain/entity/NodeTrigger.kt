package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.TriggerLastStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_trigger")
@NamedQuery(name = "NodeTrigger.findAll", query = "SELECT n FROM NodeTrigger n")
class NodeTrigger : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trigger_id", unique = true, nullable = false)
    private val triggerId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_date")
    private val changeDate: Date? = null

    @Column(nullable = false, length = 100)
    private val description: String? = null

    @Column(name = "enable_status", nullable = false)
    private val enableStatus: EnableStatusRule? = null

    @Column(nullable = false, length = 100)
    private val expression: String? = null

    @Column(name = "last_status", nullable = false)
    private val lastStatus: TriggerLastStatusRule? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private val member: Member? = null

    @OneToMany(mappedBy = "nodeTrigger")
    private val nodeTriggerActions: List<NodeTriggerAction>? = null
    fun addNodeTriggerAction(nodeTriggerAction: NodeTriggerAction): NodeTriggerAction {
        getNodeTriggerActions().add(nodeTriggerAction)
        nodeTriggerAction.setNodeTrigger(this)
        return nodeTriggerAction
    }

    fun removeNodeTriggerAction(nodeTriggerAction: NodeTriggerAction): NodeTriggerAction {
        getNodeTriggerActions().remove(nodeTriggerAction)
        nodeTriggerAction.setNodeTrigger(null)
        return nodeTriggerAction
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}