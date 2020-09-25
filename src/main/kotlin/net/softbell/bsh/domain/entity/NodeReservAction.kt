package net.softbell.bsh.domain.entity

import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 예약 액션 엔티티
 */
@Entity
@Table(name = "node_reserv_action")
@NamedQuery(name = "NodeReservAction.findAll", query = "SELECT n FROM NodeReservAction n")
class NodeReservAction : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserv_action_id", unique = true, nullable = false)
    var reservActionId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserv_id", nullable = false)
    var nodeReserv: NodeReserv? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", nullable = false)
    var nodeAction: NodeAction? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}