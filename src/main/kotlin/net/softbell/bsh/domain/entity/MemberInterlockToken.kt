package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 연동 토큰 엔티티
 */
@Entity
@Table(name = "member_interlock_token", uniqueConstraints = [UniqueConstraint(columnNames = ["token"])])
@NamedQuery(name = "MemberInterlockToken.findAll", query = "SELECT m FROM MemberInterlockToken m")
class MemberInterlockToken(
        @Column(name = "enable_status", nullable = false)
        var enableStatus: EnableStatusRule,

        @Column(name = "token", nullable = false)
        var token: String,

        @Column(name = "name", nullable = false)
        var name: String,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "register_date", nullable = false)
        var registerDate: Date,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id", nullable = false)
        var member: Member
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_interlock_id", unique = true, nullable = false)
    var memberInterlockId: Long = 0

    companion object {
        private const val serialVersionUID = 1L
    }
}