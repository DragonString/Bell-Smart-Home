package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberGroup
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹뷰 요약 카드정보 DTO
 */
class MemberGroupSummaryCardDto(entity: MemberGroup?) {
    var gid: Long?
    var name: String?
    var enableStatus: EnableStatusRule?
    var listMember: MutableList<MemberGroupItemDto>

    inner class MemberGroupItemDto(entity: Member?) {
        var memberId: Long?
        var nickname: String?

        init {
            memberId = entity!!.memberId
            nickname = entity.nickname
        }
    }

    init {
        // Exception
        entity.let {
            // Init
            listMember = ArrayList()

            // Convert
            gid = entity!!.memberGroupId
            name = entity.name
            enableStatus = entity.enableStatus
            for (child in entity.memberGroupItems!!) listMember.add(MemberGroupItemDto(child.member))
        }
    }
}