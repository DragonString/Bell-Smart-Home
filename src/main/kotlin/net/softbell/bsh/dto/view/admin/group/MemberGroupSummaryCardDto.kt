package net.softbell.bsh.dto.view.admin.group

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.MemberGroup

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹뷰 요약 카드정보 DTO
 */
class MemberGroupSummaryCardDto(entity: MemberGroup?) {
    private val gid: Long
    private val name: String
    private val enableStatus: EnableStatusRule
    private val listMember: MutableList<MemberGroupItemDto>

    inner class MemberGroupItemDto(entity: Member) {
        private val memberId: Long
        private val nickname: String

        init {
            memberId = entity.getMemberId()
            nickname = entity.getNickname()
        }
    }

    init {
        // Exception
        if (entity == null) return

        // Init
        listMember = ArrayList()

        // Convert
        gid = entity.getMemberGroupId()
        name = entity.getName()
        enableStatus = entity.getEnableStatus()
        for (child in entity.getMemberGroupItems()) listMember.add(MemberGroupItemDto(child.getMember()))
    }
}