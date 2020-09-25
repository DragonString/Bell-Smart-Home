package net.softbell.bsh.iot.dto.bshp.v1

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Item Value DTO
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class ItemValueV1Dto {
    private val itemIndex: Byte? = null
    private val itemStatus: Double? = null
}