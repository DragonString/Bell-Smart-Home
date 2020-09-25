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
 * @Description : 액션 아이템 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
class IotActionItemDto {
    private val actionItemId: Long? = null
    private val itemId: Long? = null
    private val itemStatus: Double? = null
}