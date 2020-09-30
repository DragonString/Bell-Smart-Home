package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.entity.Member

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 회원 카드 아이템 DTO
 */
class GroupMemberCardItemDto(entity: Member?) {
    var memberId: Long?
    var userId: String?
    var name: String?
    var nickname: String?
    var email: String?

    init {
        // Exception
        entity.let {
            // Convert
            memberId = entity!!.memberId
            userId = entity.userId
            name = entity.name
            nickname = entity.nickname
            email = entity.email
        }
    }
}