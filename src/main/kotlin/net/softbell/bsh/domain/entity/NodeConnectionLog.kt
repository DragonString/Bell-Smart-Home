package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.AuthStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 연결 로그 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "node_connection_log")
@NamedQuery(name = "NodeConnectionLog.findAll", query = "SELECT n FROM NodeConnectionLog n")
class NodeConnectionLog : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", unique = true, nullable = false)
    private val logId: Long? = null

    @Column(nullable = false, length = 15)
    private val ipv4: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date", nullable = false)
    private val requestDate: Date? = null

    @Column(nullable = false)
    private val status: AuthStatusRule? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false, insertable = false, updatable = false)
    private val node: Node? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}