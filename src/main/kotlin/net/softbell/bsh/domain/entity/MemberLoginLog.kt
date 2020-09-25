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
 * @Description : 회원 로그인 로그 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member_login_log", indexes = [Index(name = "IDX_PERIOD_DATE", columnList = "request_date")])
@NamedQuery(name = "MemberLoginLog.findAll", query = "SELECT m FROM MemberLoginLog m")
class MemberLoginLog : Serializable {
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
    @JoinColumn(name = "member_id", nullable = false)
    private val member: Member? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}