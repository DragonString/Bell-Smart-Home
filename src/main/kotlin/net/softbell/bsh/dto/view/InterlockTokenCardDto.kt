package net.softbell.bsh.dto.view

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.MemberInterlockToken

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 연동 토큰뷰 카드정보 DTO
 */
class InterlockTokenCardDto(entity: MemberInterlockToken?) {
    private val tokenId: Long
    private val name: String
    private val token: String
    private val enableStatus: EnableStatusRule
    private val registerDate: Date

    init {
        // Exception
        if (entity == null) return

        // Convert
        tokenId = entity.getMemberInterlockId()
        name = entity.getName()
        token = entity.getToken()
        enableStatus = entity.getEnableStatus()
        registerDate = entity.getRegisterDate()
    }
}