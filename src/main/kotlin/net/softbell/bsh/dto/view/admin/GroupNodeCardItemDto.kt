package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.entity.Node

/**
 * @author : Bell(bell@softbell.net)
 * @description : 그룹 노드 카드 아이템 DTO
 */
class GroupNodeCardItemDto(entity: Node) {
    val nodeId: Long = entity.nodeId
    val nodeName: String = entity.nodeName
    val alias: String = entity.alias
    val version: String = entity.version
}