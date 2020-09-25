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
 * @Description : 노드 예약 액션 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_reserv_action")
@NamedQuery(name = "NodeReservAction.findAll", query = "SELECT n FROM NodeReservAction n")
class NodeReservAction : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserv_action_id", unique = true, nullable = false)
    private val reservActionId: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserv_id", nullable = false)
    private val nodeReserv: NodeReserv? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_id", nullable = false)
    private val nodeAction: NodeAction? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}