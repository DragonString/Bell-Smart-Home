package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 수정뷰 정보 카드정보 DTO
 */
class NodeInfoCardDto(entity: Node) {
    val nodeId: Long = entity.nodeId
    val enableStatus: EnableStatusRule = entity.enableStatus
    val nodeName: String = entity.nodeName
    val alias: String = entity.alias
    val uid: String = entity.uid
    val token: String? = entity.token
    val controlMode: Byte = entity.controlMode
    val version: String = entity.version
    val registerDate: Date = entity.registerDate
    val approvalDate: Date? = entity.approvalDate
}