package net.softbell.bsh.dto.view

import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 프로필뷰 카드정보 DTO
 */
class MemberProfileCardDto(entity: Member) {
    val name: String = entity.name
    val nickname: String = entity.nickname
    val userId: String = entity.userId
    val email: String = entity.email
    val ban: BanRule = entity.ban
    val permission: MemberRole = entity.permission
    val registerDate: Date = entity.registerDate
}