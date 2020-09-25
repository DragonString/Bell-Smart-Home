package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 기록 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_item_history", indexes = [Index(name = "IDX_PERIOD_DATE", columnList = "receive_date")])
@NamedQuery(name = "NodeItemHistory.findAll", query = "SELECT n FROM NodeItemHistory n")
class NodeItemHistory : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_history_id", unique = true, nullable = false)
    private val itemHistoryId: Long? = null

    @Column(name = "item_status", nullable = false)
    private val itemStatus: Double? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "receive_date", nullable = false)
    private val receiveDate: Date? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private val nodeItem: NodeItem? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}