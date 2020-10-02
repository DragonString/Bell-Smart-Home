package net.softbell.bsh.domain.entity

import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 엔티티
 */
@Entity
@Table(name = "member_group")
@NamedQuery(name = "MemberGroup.findAll", query = "SELECT m FROM MemberGroup m")
class MemberGroup(
        @Column(name = "enable_status", nullable = false)
        var enableStatus: EnableStatusRule,

        @Column(nullable = false, length = 50)
        var name: String,

        @OneToMany(mappedBy = "memberGroup")
        var groupPermissions: MutableList<GroupPermission> = ArrayList(),

        @OneToMany(mappedBy = "memberGroup")
        var memberGroupItems: MutableList<MemberGroupItem> = ArrayList()
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_group_id", unique = true, nullable = false)
    var memberGroupId: Long = 0

    fun addGroupPermission(groupPermission: GroupPermission): GroupPermission? {
        groupPermissions.add(groupPermission)
        groupPermission.memberGroup = this
        return groupPermission
    }

//    fun removeGroupPermission(groupPermission: GroupPermission): GroupPermission? {
//        groupPermissions.remove(groupPermission)
//        groupPermission.memberGroup = null
//        return groupPermission
//    }

    fun addMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem? {
        memberGroupItems.add(memberGroupItem)
        memberGroupItem.memberGroup = this
        return memberGroupItem
    }

//    fun removeMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem? {
//        memberGroupItems.remove(memberGroupItem)
//        memberGroupItem.memberGroup = null
//        return memberGroupItem
//    }

    companion object {
        private const val serialVersionUID = 1L
    }
}