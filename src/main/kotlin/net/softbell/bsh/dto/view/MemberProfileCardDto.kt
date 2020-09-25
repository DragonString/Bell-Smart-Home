package net.softbell.bsh.dto.view

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.BanRule
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.Member
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 프로필뷰 카드정보 DTO
 */
@Getter
@Setter
class MemberProfileCardDto(entity: Member?) {
    private val name: String
    private val nickname: String
    private val userId: String
    private val email: String
    private val ban: BanRule
    private val permission: MemberRole
    private val registerDate: Date

    init {
        // Exception
        if (entity == null) return

        // Convert
        name = entity.getName()
        nickname = entity.getNickname()
        userId = entity.getUserId()
        email = entity.getEmail()
        ban = entity.getBan()
        permission = entity.getPermission()
        registerDate = entity.getRegisterDate()
    }
}