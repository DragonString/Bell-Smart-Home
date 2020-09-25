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
class MemberGroup : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_group_id", unique = true, nullable = false)
    var memberGroupId: Long? = null

    @Column(name = "enable_status", nullable = false)
    var enableStatus: EnableStatusRule? = null

    @Column(nullable = false, length = 50)
    var name: String? = null

    @OneToMany(mappedBy = "memberGroup")
    var groupPermissions: List<GroupPermission>? = null

    @OneToMany(mappedBy = "memberGroup")
    var memberGroupItems: List<MemberGroupItem>? = null
    
    
//    fun addGroupPermission(groupPermission: GroupPermission): GroupPermission {
//        getGroupPermissions().add(groupPermission)
//        groupPermission.setMemberGroup(this)
//        return groupPermission
//    }
//
//    fun removeGroupPermission(groupPermission: GroupPermission): GroupPermission {
//        getGroupPermissions().remove(groupPermission)
//        groupPermission.setMemberGroup(null)
//        return groupPermission
//    }
//
//    fun addMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
//        getMemberGroupItems().add(memberGroupItem)
//        memberGroupItem.setMemberGroup(this)
//        return memberGroupItem
//    }
//
//    fun removeMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
//        getMemberGroupItems().remove(memberGroupItem)
//        memberGroupItem.setMemberGroup(null)
//        return memberGroupItem
//    }

    companion object {
        private const val serialVersionUID = 1L
    }
}