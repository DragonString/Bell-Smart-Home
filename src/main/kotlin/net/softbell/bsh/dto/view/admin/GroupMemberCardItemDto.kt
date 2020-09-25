package net.softbell.bsh.dto.view.admin

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.Member
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 회원 카드 아이템 DTO
 */
@Getter
@Setter
class GroupMemberCardItemDto(entity: Member?) {
    private val memberId: Long
    private val userId: String
    private val name: String
    private val nickname: String
    private val email: String

    init {
        // Exception
        if (entity == null) return

        // Convert
        memberId = entity.getMemberId()
        userId = entity.getUserId()
        name = entity.getName()
        nickname = entity.getNickname()
        email = entity.getEmail()
    }
}