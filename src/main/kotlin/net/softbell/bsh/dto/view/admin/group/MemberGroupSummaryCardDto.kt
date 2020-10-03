package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberGroup

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 그룹뷰 요약 카드정보 DTO
 */
class MemberGroupSummaryCardDto(entity: MemberGroup) {
    val gid: Long = entity.memberGroupId
    val name: String = entity.name
    val enableStatus: EnableStatusRule = entity.enableStatus
    val listMember: MutableList<MemberGroupItemDto> = ArrayList()

    inner class MemberGroupItemDto(entity: Member) {
        val memberId: Long = entity.memberId
        val nickname: String = entity.nickname
    }

    init {
        // Convert
        for (child in entity.memberGroupItems)
            listMember.add(MemberGroupItemDto(child.member))
    }
}