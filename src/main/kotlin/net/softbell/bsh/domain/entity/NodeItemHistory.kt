package net.softbell.bsh.domain.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 기록 엔티티
 */
@Entity
@Table(name = "node_item_history", indexes = [Index(name = "IDX_NODE_ITEM_HISTORY_RECEIVE_DATE", columnList = "receive_date")])
@NamedQuery(name = "NodeItemHistory.findAll", query = "SELECT n FROM NodeItemHistory n")
class NodeItemHistory(
        @Column(name = "item_status", nullable = false)
        var itemStatus: Double,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "receive_date", nullable = false)
        var receiveDate: Date,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "item_id", nullable = false)
        var nodeItem: NodeItem
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_history_id", unique = true, nullable = false)
    var itemHistoryId: Long = 0

    companion object {
        private const val serialVersionUID = 1L
    }
}