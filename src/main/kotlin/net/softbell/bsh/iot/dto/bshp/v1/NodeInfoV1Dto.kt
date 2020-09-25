package net.softbell.bsh.iot.dto.bshp.v1

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Node Info DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class NodeInfoV1Dto {
    private val uid: String? = null
    private val controlMode: Byte? = null
    private val nodeName: String? = null
    private val version: String? = null
}