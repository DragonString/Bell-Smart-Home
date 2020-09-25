package net.softbell.bsh.dto.view.admin

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.Node
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 노드 카드 아이템 DTO
 */
@Getter
@Setter
class GroupNodeCardItemDto(entity: Node?) {
    private val nodeId: Long
    private val nodeName: String
    private val alias: String
    private val version: String

    init {
        // Exception
        if (entity == null) return

        // Convert
        nodeId = entity.getNodeId()
        nodeName = entity.getNodeName()
        alias = entity.getAlias()
        version = entity.getVersion()
    }
}