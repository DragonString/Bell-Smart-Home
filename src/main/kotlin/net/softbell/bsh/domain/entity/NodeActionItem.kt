package net.softbell.bsh.domain.entity

import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 아이템 엔티티
 */
@Entity
@Table(name = "node_action_item", uniqueConstraints = [UniqueConstraint(columnNames = ["item_id", "action_id"])])
@NamedQuery(name = "NodeActionItem.findAll", query = "SELECT n FROM NodeActionItem n")
class NodeActionItem(
        @Column(name = "item_status", nullable = false)
        var itemStatus: Double,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "item_id", nullable = false)
        var nodeItem: NodeItem,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "action_id", nullable = false)
        var nodeAction: NodeAction
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_item_id", unique = true, nullable = false)
    var actionItemId: Long = 0

    companion object {
        private const val serialVersionUID = 1L
    }
}