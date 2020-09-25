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
 * @Description : 노드 그룹 권한 정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
class NodeGroupPermissionDto {
    private val memberGid: Long? = null
    private val permission: Int? = null
}