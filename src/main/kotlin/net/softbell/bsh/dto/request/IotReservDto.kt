package net.softbell.bsh.dto.request

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
class IotReservDto {
    private val enableStatus = false
    private val description: String? = null
    private val expression: String? = null
    private val mapAction: HashMap<Long, IotActionDto>? = null
}