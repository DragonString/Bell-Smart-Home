package net.softbell.bsh.iot.dto.bshp.v1

/**
 * @author : Bell(bell@softbell.net)
 * @description : BSHPv1 전용 Item Value DTO
 */
data class ItemValueV1Dto (
    var itemIndex: Byte,
    var itemStatus: Double?
)