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

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 엔티티
 */
@Entity
@Table(name = "member")
@NamedQuery(name = "Member.findAll", query = "SELECT m FROM Member m")
class Member : Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    var memberId: Long? = null

    @Column(nullable = false)
    var ban: BanRule? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ban_date")
    var banDate: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "login_fail_ban_start")
    var loginFailBanStart: Date? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "change_passwd_date")
    var changePasswdDate: Date? = null

    @Column(name = "email", nullable = false, length = 200)
    var email: String? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_login")
    var lastLogin: Date? = null

    @Column(nullable = false, length = 40)
    var nickname: String? = null

    @Column(nullable = false, length = 64)
    var passwd: String? = null

    @Column(nullable = false)
    var permission: MemberRole? = null

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date", nullable = false)
    var registerDate: Date? = null

    @Column(name = "user_id", nullable = false, length = 50)
    var userId: String? = null

    @Column(nullable = false, length = 10)
    var name: String? = null

    @OneToMany(mappedBy = "member")
    var memberGroupItems: List<MemberGroupItem>? = null

    @OneToMany(mappedBy = "member")
    var memberLoginLogs: List<MemberLoginLog>? = null

    @OneToMany(mappedBy = "member")
    var nodeActions: List<NodeAction>? = null

    @OneToMany(mappedBy = "member")
    var nodeReservs: List<NodeReserv>? = null

    @OneToMany(mappedBy = "member")
    var nodeTriggers: List<NodeTrigger>? = null


//    fun addMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
//        getMemberGroupItems().add(memberGroupItem)
//        memberGroupItem.setMember(this)
//        return memberGroupItem
//    }
//
//    fun removeMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
//        getMemberGroupItems().remove(memberGroupItem)
//        memberGroupItem.setMember(null)
//        return memberGroupItem
//    }
//
//    fun addMemberLoginLog(memberLoginLog: MemberLoginLog): MemberLoginLog {
//        getMemberLoginLogs().add(memberLoginLog)
//        memberLoginLog.setMember(this)
//        return memberLoginLog
//    }
//
//    fun removeMemberLoginLog(memberLoginLog: MemberLoginLog): MemberLoginLog {
//        getMemberLoginLogs().remove(memberLoginLog)
//        memberLoginLog.setMember(null)
//        return memberLoginLog
//    }
//
//    fun addNodeAction(nodeAction: NodeAction): NodeAction {
//        getNodeActions().add(nodeAction)
//        nodeAction.setMember(this)
//        return nodeAction
//    }
//
//    fun removeNodeAction(nodeAction: NodeAction): NodeAction {
//        getNodeActions().remove(nodeAction)
//        nodeAction.setMember(null)
//        return nodeAction
//    }
//
//    fun addNodeReserv(nodeReserv: NodeReserv): NodeReserv {
//        getNodeReservs().add(nodeReserv)
//        nodeReserv.setMember(this)
//        return nodeReserv
//    }
//
//    fun removeNodeReserv(nodeReserv: NodeReserv): NodeReserv {
//        getNodeReservs().remove(nodeReserv)
//        nodeReserv.setMember(null)
//        return nodeReserv
//    }
//
//    fun addNodeTrigger(nodeTrigger: NodeTrigger): NodeTrigger {
//        getNodeTriggers().add(nodeTrigger)
//        nodeTrigger.setMember(this)
//        return nodeTrigger
//    }
//
//    fun removeNodeTrigger(nodeTrigger: NodeTrigger): NodeTrigger {
//        getNodeTriggers().remove(nodeTrigger)
//        nodeTrigger.setMember(null)
//        return nodeTrigger
//    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        // Field
        val listAuth: MutableList<GrantedAuthority> = ArrayList()

        // Load
        listAuth.add(SimpleGrantedAuthority(permission?.value))

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

    override fun getPassword(): String {
        //return passwd ?: ""
        return passwd!!
    }

    override fun isEnabled(): Boolean {
        return if (ban == BanRule.NORMAL) true else false
    }

    override fun getUsername(): String? {
        return userId
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}