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
class NodeReserv : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserv_id", unique = true, nullable = false)
    var reservId: Long? = null

    @Column(nullable = false, length = 50)
    var description: String? = null

    @Column(name = "enable_status", nullable = false)
    var enableStatus: EnableStatusRule? = null

    @Column(nullable = false, length = 100)
    var expression: String? = null

    @OneToMany(mappedBy = "nodeReserv")
    var nodeReservActions: List<NodeReservAction>? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null


//    fun addNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction {
//        getNodeReservActions().add(nodeReservAction)
//        nodeReservAction.setNodeReserv(this)
//        return nodeReservAction
//    }
//
//    fun removeNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction {
//        getNodeReservActions().remove(nodeReservAction)
//        nodeReservAction.setNodeReserv(null)
//        return nodeReservAction
//    }

    companion object {
        private const val serialVersionUID = 1L
    }
}