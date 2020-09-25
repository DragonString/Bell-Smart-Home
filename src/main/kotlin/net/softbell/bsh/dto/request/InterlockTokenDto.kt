package net.softbell.bsh.dto.request

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 연동 토큰 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
class InterlockTokenDto {
    private val memberInterlockId: Long? = null
    private val name: String? = null
}