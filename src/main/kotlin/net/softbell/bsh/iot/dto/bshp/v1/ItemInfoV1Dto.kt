package net.softbell.bsh.iot.dto.bshp.v1

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Items Info DTO
 */
data class ItemInfoV1Dto (
        val controlMode: Byte?,
        val itemIndex: Byte?,
        val itemMode: Int?,
        val itemCategory: Int?,
        val itemType: Int?,
        val itemName: String?
)