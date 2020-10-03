package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 관리뷰 요약 카드정보 DTO
 */
class MemberSummaryCardDto(entity: Member) {
    val memberId: Long = entity.memberId
    val userId: String = entity.userId
    val username: String = entity.username
    val registerDate: Date = entity.registerDate
    val ban: BanRule = entity.ban
    val permission: MemberRole = entity.permission
}