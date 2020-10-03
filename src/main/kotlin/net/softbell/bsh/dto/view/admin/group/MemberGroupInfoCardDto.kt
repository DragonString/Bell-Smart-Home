package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberGroup
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 그룹 정보 카드 DTO
 */
class MemberGroupInfoCardDto(entity: MemberGroup) {
    val gid: Long = entity.memberGroupId
    val enableStatus: EnableStatusRule = entity.enableStatus
    val name: String = entity.name
    val listMembers: MutableList<MemberGroupInfoCardMember> = ArrayList()
    val listPermissions: MutableList<MemberGroupInfoCardPermission> = ArrayList()

    inner class MemberGroupInfoCardMember(entity: Member) {
        val memberId: Long = entity.memberId
        val userId: String = entity.userId
        val name: String = entity.name
        val nickname: String = entity.nickname
        val email: String = entity.email
    }

    inner class MemberGroupInfoCardPermission(entity: GroupPermission) {
        val pid: Long = entity.groupPermissionId
        val gid: Long = entity.nodeGroup.nodeGroupId
        val name: String = entity.nodeGroup.name
        val permission: GroupRole = entity.groupPermission
        val assignDate: Date = entity.assignDate
    }

    init {
        // Convert
        for (child in entity.memberGroupItems)
            listMembers.add(MemberGroupInfoCardMember(child.member))
        for (child in entity.groupPermissions)
            listPermissions.add(MemberGroupInfoCardPermission(child))
    }
}