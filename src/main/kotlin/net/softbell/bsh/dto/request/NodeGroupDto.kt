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
 * @Description : 그룹 노드 정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
class NodeGroupDto {
    private val enableStatus = false
    private val name: String? = null
    private val nodeId: List<Long>? = null
}