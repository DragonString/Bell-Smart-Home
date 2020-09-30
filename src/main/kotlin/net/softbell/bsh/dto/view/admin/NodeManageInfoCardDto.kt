package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리 수정뷰 정보 카드정보 DTO
 */
class NodeManageInfoCardDto(entity: Node?) {
    var nodeId: Long?
    var enableStatus: EnableStatusRule?
    var nodeName: String?
    var alias: String?
    var uid: String?
    var token: String?
    var controlMode: Byte?
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
            registerDate = entity.registerDate
            approvalDate = entity.approvalDate
        }
    }
}