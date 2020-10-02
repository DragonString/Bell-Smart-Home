package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 예약 엔티티
 */
@Entity
@Table(name = "node_reserv")
@NamedQuery(name = "NodeReserv.findAll", query = "SELECT n FROM NodeReserv n")
class NodeReserv(
        @Column(nullable = false, length = 50)
        var description: String,

        @Column(name = "enable_status", nullable = false)
        var enableStatus: EnableStatusRule,

        @Column(nullable = false, length = 100)
        var expression: String,

        @OneToMany(mappedBy = "nodeReserv")
        var nodeReservActions: MutableList<NodeReservAction> = ArrayList(),

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id", nullable = false)
        var member: Member
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserv_id", unique = true, nullable = false)
    var reservId: Long = 0

    fun addNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction? {
        nodeReservActions.add(nodeReservAction)
        nodeReservAction.nodeReserv = this
        return nodeReservAction
    }

//    fun removeNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction? {
//        nodeReservActions.remove(nodeReservAction)
//        nodeReservAction.nodeReserv = null
//        return nodeReservAction
//    }

    companion object {
        private const val serialVersionUID = 1L
    }
}