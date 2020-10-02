package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 연동 토큰 DTO
 */
data class InterlockTokenDto (
        var memberInterlockId: Long? = null,
        var name: String
)