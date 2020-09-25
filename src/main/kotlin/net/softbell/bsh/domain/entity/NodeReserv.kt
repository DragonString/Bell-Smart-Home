package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 예약 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_reserv")
@NamedQuery(name = "NodeReserv.findAll", query = "SELECT n FROM NodeReserv n")
class NodeReserv : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserv_id", unique = true, nullable = false)
    private val reservId: Long? = null

    @Column(nullable = false, length = 50)
    private val description: String? = null

    @Column(name = "enable_status", nullable = false)
    private val enableStatus: EnableStatusRule? = null

    @Column(nullable = false, length = 100)
    private val expression: String? = null

    @OneToMany(mappedBy = "nodeReserv")
    private val nodeReservActions: List<NodeReservAction>? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private val member: Member? = null
    fun addNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction {
        getNodeReservActions().add(nodeReservAction)
        nodeReservAction.setNodeReserv(this)
        return nodeReservAction
    }

    fun removeNodeReservAction(nodeReservAction: NodeReservAction): NodeReservAction {
        getNodeReservActions().remove(nodeReservAction)
        nodeReservAction.setNodeReserv(null)
        return nodeReservAction
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}