package net.softbell.bsh.dto.view.admin

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 노드 카드 아이템 DTO
 */
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