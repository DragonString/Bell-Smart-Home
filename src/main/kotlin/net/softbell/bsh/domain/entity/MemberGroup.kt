package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import java.io.Serializable
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member_group")
@NamedQuery(name = "MemberGroup.findAll", query = "SELECT m FROM MemberGroup m")
class MemberGroup : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_group_id", unique = true, nullable = false)
    private val memberGroupId: Long? = null

    @Column(name = "enable_status", nullable = false)
    private val enableStatus: EnableStatusRule? = null

    @Column(nullable = false, length = 50)
    private val name: String? = null

    @OneToMany(mappedBy = "memberGroup")
    private val groupPermissions: List<GroupPermission>? = null

    @OneToMany(mappedBy = "memberGroup")
    private val memberGroupItems: List<MemberGroupItem>? = null
    fun addGroupPermission(groupPermission: GroupPermission): GroupPermission {
        getGroupPermissions().add(groupPermission)
        groupPermission.setMemberGroup(this)
        return groupPermission
    }

    fun removeGroupPermission(groupPermission: GroupPermission): GroupPermission {
        getGroupPermissions().remove(groupPermission)
        groupPermission.setMemberGroup(null)
        return groupPermission
    }

    fun addMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
        getMemberGroupItems().add(memberGroupItem)
        memberGroupItem.setMemberGroup(this)
        return memberGroupItem
    }

    fun removeMemberGroupItem(memberGroupItem: MemberGroupItem): MemberGroupItem {
        getMemberGroupItems().remove(memberGroupItem)
        memberGroupItem.setMemberGroup(null)
        return memberGroupItem
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}