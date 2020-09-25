package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_action_item", uniqueConstraints = [UniqueConstraint(columnNames = ["item_id", "action_id"])])
@NamedQuery(name = "NodeActionItem.findAll", query = "SELECT n FROM NodeActionItem n")
class NodeActionItem : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_item_id", unique = true, nullable = false)
    private val actionItemId: Long? = null

    @Column(name = "item_status", nullable = false)
    private val itemStatus: Double? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private val nodeItem: NodeItem? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", nullable = false)
    private val nodeAction: NodeAction? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}