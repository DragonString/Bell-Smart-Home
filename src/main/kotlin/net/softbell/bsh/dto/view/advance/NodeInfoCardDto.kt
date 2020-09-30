package net.softbell.bsh.dto.view.advance

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 수정뷰 정보 카드정보 DTO
 */
class NodeInfoCardDto(entity: Node?) {
    var nodeId: Long?
    var enableStatus: EnableStatusRule?
    var nodeName: String?
    var alias: String?
    var uid: String?
    var token: String?
    var controlMode: Byte?
    var version: String?
    var registerDate: Date?
    var approvalDate: Date?

    init {
        // Exception
        entity.let {
            // Convert
            nodeId = entity!!.nodeId
            enableStatus = entity.enableStatus
            nodeName = entity.nodeName
            alias = entity.alias
            uid = entity.uid
            token = entity.token
            controlMode = entity.controlMode
            version = entity.version
            registerDate = entity.registerDate
            approvalDate = entity.approvalDate
        }
    }
}