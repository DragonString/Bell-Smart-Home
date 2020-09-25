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
 * @Description : 노드 그룹 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_group_item", uniqueConstraints = [UniqueConstraint(columnNames = ["node_group_id", "node_id"])])
@NamedQuery(name = "NodeGroupItem.findAll", query = "SELECT n FROM NodeGroupItem n")
class NodeGroupItem : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_item_id", unique = true, nullable = false)
    private val groupItemId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assign_date", nullable = false)
    private val assignDate: Date? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_group_id", nullable = false)
    private val nodeGroup: NodeGroup? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private val node: Node? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}