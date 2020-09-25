package net.softbell.bsh.iot.dto.bshp.v1

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Items Info DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class ItemInfoV1Dto {
    private val controlMode: Byte? = null
    private val itemIndex: Byte? = null
    private val itemMode: Int? = null
    private val itemCategory: Int? = null
    private val itemType: Int? = null
    private val itemName: String? = null
}