package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.TriggerStatusRule
import java.io.Serializable
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 액션 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_trigger_action")
@NamedQuery(name = "NodeTriggerAction.findAll", query = "SELECT n FROM NodeTriggerAction n")
class NodeTriggerAction : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trigger_action_id", unique = true, nullable = false)
    private val triggerActionId: Long? = null

    @Column(name = "trigger_status", nullable = false)
    private val triggerStatus: TriggerStatusRule? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trigger_id", nullable = false)
    private val nodeTrigger: NodeTrigger? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", nullable = false)
    private val nodeAction: NodeAction? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}