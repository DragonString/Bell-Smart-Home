package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.MemberGroup

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 정보 카드 DTO
 */
class MemberGroupInfoCardDto(entity: MemberGroup?) {
    private val gid: Long
    private val enableStatus: EnableStatusRule
    private val name: String
    private val listMembers: MutableList<MemberGroupInfoCardMember>
    private val listPermissions: MutableList<MemberGroupInfoCardPermission>

    inner class MemberGroupInfoCardMember(entity: Member) {
        private val memberId: Long
        private val userId: String
        private val name: String
        private val nickname: String
        private val email: String

        init {
            // Convert
            memberId = entity.getMemberId()
            userId = entity.getUserId()
            this.name = entity.getName()
            nickname = entity.getNickname()
            email = entity.getEmail()
        }
    }

    inner class MemberGroupInfoCardPermission(entity: GroupPermission) {
        private val pid: Long
        private val gid: Long
        private val name: String
        private val permission: GroupRole
        private val assignDate: Date

        init {
            pid = entity.getGroupPermissionId()
            this.gid = entity.getNodeGroup().getNodeGroupId()
            this.name = entity.getNodeGroup().getName()
            permission = entity.getGroupPermission()
            assignDate = entity.getAssignDate()
        }
    }

    init {
        // Exception
        if (entity == null) return

        // Init
        listMembers = ArrayList()
        listPermissions = ArrayList()

        // Convert
        gid = entity.getMemberGroupId()
        enableStatus = entity.getEnableStatus()
        name = entity.getName()
        for (child in entity.getMemberGroupItems()) listMembers.add(MemberGroupInfoCardMember(child.getMember()))
        for (child in entity.getGroupPermissions()) listPermissions.add(MemberGroupInfoCardPermission(child))
    }
}