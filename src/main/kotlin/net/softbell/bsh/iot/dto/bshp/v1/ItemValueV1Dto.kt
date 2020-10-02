package net.softbell.bsh.iot.dto.bshp.v1

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Item Value DTO
 */
data class ItemValueV1Dto (
    var itemIndex: Byte,
    var itemStatus: Double?
)