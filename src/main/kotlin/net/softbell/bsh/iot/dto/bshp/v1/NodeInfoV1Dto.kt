package net.softbell.bsh.iot.dto.bshp.v1

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Node Info DTO
 */
data class NodeInfoV1Dto (
        val uid: String?,
        val controlMode: Byte?,
        val nodeName: String?,
        val version: String?
)