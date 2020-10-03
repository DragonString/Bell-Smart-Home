package net.softbell.bsh.iot.dto.bshp.v1

/**
 * @author : Bell(bell@softbell.net)
 * @description : BSHPv1 전용 Node Info DTO
 */
data class NodeInfoV1Dto (
    var uid: String,
    var controlMode: Byte,
    var nodeName: String,
    var version: String
)