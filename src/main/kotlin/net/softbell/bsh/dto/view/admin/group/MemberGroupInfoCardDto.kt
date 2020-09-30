package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberGroup
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 정보 카드 DTO
 */
class MemberGroupInfoCardDto(entity: MemberGroup?) {
    var gid: Long?
    var enableStatus: EnableStatusRule?
    var name: String?
    var listMembers: MutableList<MemberGroupInfoCardMember>
    var listPermissions: MutableList<MemberGroupInfoCardPermission>

    inner class MemberGroupInfoCardMember(entity: Member?) {
        var memberId: Long?
        var userId: String?
        var name: String?
        var nickname: String?
        var email: String?

        init {
            // Convert
            memberId = entity!!.memberId
            userId = entity.userId
            this.name = entity.name
            nickname = entity.nickname
            email = entity.email
        }
    }

    inner class MemberGroupInfoCardPermission(entity: GroupPermission) {
        var pid: Long?
        var gid: Long?
        var name: String?
        var permission: GroupRole?
        var assignDate: Date?

        init {
            pid = entity.groupPermissionId
            this.gid = entity.nodeGroup!!.nodeGroupId
            this.name = entity.nodeGroup!!.name
            permission = entity.groupPermission
            assignDate = entity.assignDate
        }
    }

    init {
        // Exception
        entity.let {

            // Init
            listMembers = ArrayList()
            listPermissions = ArrayList()

            // Convert
            gid = entity!!.memberGroupId
            enableStatus = entity.enableStatus
            name = entity.name
            for (child in entity.memberGroupItems!!) listMembers.add(MemberGroupInfoCardMember(child.member))
            for (child in entity.groupPermissions!!) listPermissions.add(MemberGroupInfoCardPermission(child))
        }
    }
}