package net.softbell.bsh.domain.entity

import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 아이템 엔티티
 */
@Entity
@Table(name = "node_group_item", uniqueConstraints = [UniqueConstraint(columnNames = ["node_group_id", "node_id"])])
@NamedQuery(name = "NodeGroupItem.findAll", query = "SELECT n FROM NodeGroupItem n")
class NodeGroupItem : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_item_id", unique = true, nullable = false)
    var groupItemId: Long? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "assign_date", nullable = false)
    var assignDate: Date? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_group_id", nullable = false)
    var nodeGroup: NodeGroup? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    var node: Node? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}