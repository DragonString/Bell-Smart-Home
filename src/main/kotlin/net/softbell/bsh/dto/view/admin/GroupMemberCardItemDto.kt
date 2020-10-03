package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.entity.Member

/**
 * @author : Bell(bell@softbell.net)
 * @description : 그룹 회원 카드 아이템 DTO
 */
class GroupMemberCardItemDto(entity: Member) {
    val memberId: Long = entity.memberId
    val userId: String = entity.userId
    val name: String = entity.name
    val nickname: String = entity.nickname
    val email: String = entity.email
}