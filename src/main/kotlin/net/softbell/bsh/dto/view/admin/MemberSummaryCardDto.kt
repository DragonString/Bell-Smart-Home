package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 관리뷰 요약 카드정보 DTO
 */
class MemberSummaryCardDto(entity: Member?) {
    private val memberId: Long
    private val userId: String
    private val username: String?
    private val registerDate: Date
    private val ban: BanRule
    private val permission: MemberRole

    init {
        // Exception
        if (entity == null) return

        // Convert
        memberId = entity.getMemberId()
        userId = entity.getUserId()
        username = entity!!.username
        registerDate = entity.getRegisterDate()
        ban = entity.getBan()
        permission = entity.getPermission()
    }
}