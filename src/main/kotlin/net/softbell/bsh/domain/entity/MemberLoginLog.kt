package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.AuthStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 로그인 로그 엔티티
 */
@Entity
@Table(name = "member_login_log", indexes = [Index(name = "IDX_PERIOD_DATE", columnList = "request_date")])
@NamedQuery(name = "MemberLoginLog.findAll", query = "SELECT m FROM MemberLoginLog m")
class MemberLoginLog : Serializable {
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
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}