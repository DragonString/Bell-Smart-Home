package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.AuthStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 연결 로그 엔티티
 */
@Entity
@Table(name = "node_connection_log")
@NamedQuery(name = "NodeConnectionLog.findAll", query = "SELECT n FROM NodeConnectionLog n")
class NodeConnectionLog : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id", unique = true, nullable = false)
    var logId: Long? = null

    @Column(nullable = false, length = 15)
    var ipv4: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "request_date", nullable = false)
    var requestDate: Date? = null

    @Column(nullable = false)
    var status: AuthStatusRule? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false, insertable = false, updatable = false)
    var node: Node? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}