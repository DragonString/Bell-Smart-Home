package net.softbell.bsh.domain.entity

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member")
@NamedQuery(name = "Member.findAll", query = "SELECT m FROM Member m")
class Member : Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private val memberId: Long? = null

    @Column(nullable = false)
    private val ban: BanRule? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ban_date")
    private val banDate: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "login_fail_ban_start")
    private val loginFailBanStart: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_passwd_date")
    private val changePasswdDate: Date? = null

    @Column(name = "email", nullable = false, length = 200)
    private val email: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login")
    private val lastLogin: Date? = null

    @Column(nullable = false, length = 40)
    private val nickname: String? = null

    @Column(nullable = false, length = 64)
    private val password: String? = null

    @Column(nullable = false)
    private val permission: MemberRole? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date", nullable = false)
    private val registerDate: Date? = null

    @Column(name = "user_id", nullable = false, length = 50)
    private val userId: String? = null

    @Column(nullable = false, length = 10)
    private val name: String? = null

    @OneToMany(mappedBy = "member")
    private val memberGroupItems: List<MemberGroupItem>? = null

    @OneToMany(mappedBy = "member")
    private val memberLoginLogs: List<MemberLoginLog>? = null

    @OneToMany(mappedBy = "member")
    private val nodeActions: List<NodeAction>? = null

    @OneToMany(mappedBy = "member")
    private val nodeReservs: List<NodeReserv>? = null

    @OneToMany(mappedBy = "member")
    private val nodeTriggers: List<NodeTrigger>? = null
    fun addMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
        getMemberGroupItems().add(memberGroupItem)
        memberGroupItem.setMember(this)
        return memberGroupItem
    }

    fun removeMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
        getMemberGroupItems().remove(memberGroupItem)
        memberGroupItem.setMember(null)
        return memberGroupItem
    }

    fun addMemberLoginLog(memberLoginLog: MemberLoginLog): MemberLoginLog {
        getMemberLoginLogs().add(memberLoginLog)
        memberLoginLog.setMember(this)
        return memberLoginLog
    }

    fun removeMemberLoginLog(memberLoginLog: MemberLoginLog): MemberLoginLog {
        getMemberLoginLogs().remove(memberLoginLog)
        memberLoginLog.setMember(null)
        return memberLoginLog
    }

    fun addNodeAction(nodeAction: NodeAction): NodeAction {
        getNodeActions().add(nodeAction)
        nodeAction.setMember(this)
        return nodeAction
    }

    fun removeNodeAction(nodeAction: NodeAction): NodeAction {
        getNodeActions().remove(nodeAction)
        nodeAction.setMember(null)
        return nodeAction
    }

    fun addNodeReserv(nodeReserv: NodeReserv): NodeReserv {
        getNodeReservs().add(nodeReserv)
        nodeReserv.setMember(this)
        return nodeReserv
    }

    fun removeNodeReserv(nodeReserv: NodeReserv): NodeReserv {
        getNodeReservs().remove(nodeReserv)
        nodeReserv.setMember(null)
        return nodeReserv
    }

    fun addNodeTrigger(nodeTrigger: NodeTrigger): NodeTrigger {
        getNodeTriggers().add(nodeTrigger)
        nodeTrigger.setMember(this)
        return nodeTrigger
    }

    fun removeNodeTrigger(nodeTrigger: NodeTrigger): NodeTrigger {
        getNodeTriggers().remove(nodeTrigger)
        nodeTrigger.setMember(null)
        return nodeTrigger
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        // Field
        val listAuth: MutableList<GrantedAuthority> = ArrayList()

        // Load
        listAuth.add(SimpleGrantedAuthority(permission.getValue()))

        // Return
        return listAuth
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return if (ban == BanRule.NORMAL) true else false
    }

    override fun getUsername(): String {
        return getUserId()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}