package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 연동 토큰 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member_interlock_token", uniqueConstraints = [UniqueConstraint(columnNames = ["token"])])
@NamedQuery(name = "MemberInterlockToken.findAll", query = "SELECT m FROM MemberInterlockToken m")
class MemberInterlockToken : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_interlock_id", unique = true, nullable = false)
    private val memberInterlockId: Long? = null

    @Column(name = "enable_status", nullable = false)
    private val enableStatus: EnableStatusRule? = null

    @Column(name = "token", nullable = false)
    private val token: String? = null

    @Column(name = "name", nullable = false)
    private val name: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date", nullable = false)
    private val registerDate: Date? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private val member: Member? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}