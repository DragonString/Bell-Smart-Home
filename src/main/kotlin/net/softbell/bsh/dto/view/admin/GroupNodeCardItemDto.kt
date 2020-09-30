package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.entity.Node

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 노드 카드 아이템 DTO
 */
class GroupNodeCardItemDto(entity: Node?) {
    var nodeId: Long?
    var nodeName: String?
    var alias: String?
    var version: String?

    init {
        // Exception
        entity.let {
            // Convert
            nodeId = entity!!.nodeId
            nodeName = entity.nodeName
            alias = entity.alias
            version = entity.version
        }
    }
}