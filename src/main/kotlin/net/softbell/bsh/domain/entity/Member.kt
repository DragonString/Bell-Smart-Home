package net.softbell.bsh.domain.entity

import com.fasterxml.jackson.annotation.JsonProperty
import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.collections.ArrayList

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 엔티티
 */
@Entity
@Table(name = "member")
@NamedQuery(name = "Member.findAll", query = "SELECT m FROM Member m")
class Member(
        @Column(nullable = false)
        var ban: BanRule,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "ban_date")
        var banDate: Date? = null,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "login_fail_ban_start")
        var loginFailBanStart: Date? = null,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "change_passwd_date")
        var changePasswdDate: Date? = null,

        @Column(name = "email", nullable = false, length = 200)
        var email: String,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "last_login")
        var lastLogin: Date? = null,

        @Column(nullable = false, length = 40)
        var nickname: String,

        @Column(nullable = false, length = 64)
        private var password: String,

        @Column(nullable = false)
        var permission: MemberRole,

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "register_date", nullable = false)
        var registerDate: Date,

        @Column(name = "user_id", nullable = false, length = 50)
        var userId: String,

        @Column(nullable = false, length = 10)
        var name: String,

        @OneToMany(mappedBy = "member")
        var memberGroupItems: MutableList<MemberGroupItem> = ArrayList(),

        @OneToMany(mappedBy = "member")
        var memberLoginLogs: MutableList<MemberLoginLog> = ArrayList(),

        @OneToMany(mappedBy = "member")
        var nodeActions: MutableList<NodeAction> = ArrayList(),

        @OneToMany(mappedBy = "member")
        var nodeReservs: MutableList<NodeReserv> = ArrayList(),

        @OneToMany(mappedBy = "member")
        var nodeTriggers: MutableList<NodeTrigger> = ArrayList()
) : Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    var memberId: Long = 0

    fun addMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
        memberGroupItems.add(memberGroupItem)
        memberGroupItem.member = this
        return memberGroupItem
    }

//    fun removeMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
//        memberGroupItems.remove(memberGroupItem)
//        memberGroupItem.member = null
//        return memberGroupItem
//    }

    fun addMemberLoginLog(memberLoginLog: MemberLoginLog): MemberLoginLog {
        memberLoginLogs.add(memberLoginLog)
        memberLoginLog.member = this
        return memberLoginLog
    }

//    fun removeMemberLoginLog(memberLoginLog: MemberLoginLog): MemberLoginLog {
//        memberLoginLogs.remove(memberLoginLog)
//        memberLoginLog.member = null
//        return memberLoginLog
//    }

    fun addNodeAction(nodeAction: NodeAction): NodeAction {
        nodeActions.add(nodeAction)
        nodeAction.member = this
        return nodeAction
    }

    fun removeNodeAction(nodeAction: NodeAction): NodeAction {
        nodeActions.remove(nodeAction)
        nodeAction.member = null
        return nodeAction
    }

    fun addNodeReserv(nodeReserv: NodeReserv): NodeReserv {
        nodeReservs.add(nodeReserv)
        nodeReserv.member = this
        return nodeReserv
    }

//    fun removeNodeReserv(nodeReserv: NodeReserv): NodeReserv {
//        nodeReservs.remove(nodeReserv)
//        nodeReserv.member = null
//        return nodeReserv
//    }

    fun addNodeTrigger(nodeTrigger: NodeTrigger): NodeTrigger {
        nodeTriggers.add(nodeTrigger)
        nodeTrigger.member = this
        return nodeTrigger
    }

//    fun removeNodeTrigger(nodeTrigger: NodeTrigger): NodeTrigger {
//        nodeTriggers.remove(nodeTrigger)
//        nodeTrigger.member = null
//        return nodeTrigger
//    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        // Field
        val listAuth: MutableList<GrantedAuthority> = ArrayList()

        // Load
        listAuth.add(SimpleGrantedAuthority(permission.value))

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
        return ban == BanRule.NORMAL
    }

    override fun getUsername(): String {
        return userId
    }

    override fun getPassword(): String {
        return password
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}