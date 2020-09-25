package net.softbell.bsh.dto.view.admin

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리 수정뷰 정보 카드정보 DTO
 */
@Getter
@Setter
class NodeManageInfoCardDto(entity: Node?) {
    private val nodeId: Long
    private val enableStatus: EnableStatusRule
    private val nodeName: String
    private val alias: String
    private val uid: String
    private val token: String
    private val controlMode: Byte
    private val registerDate: Date
    private val approvalDate: Date

    init {
        // Exception
        if (entity == null) return

        // Convert
        nodeId = entity.getNodeId()
        enableStatus = entity.getEnableStatus()
        nodeName = entity.getNodeName()
        alias = entity.getAlias()
        uid = entity.getUid()
        token = entity.getToken()
        controlMode = entity.getControlMode()
        registerDate = entity.getRegisterDate()
        approvalDate = entity.getApprovalDate()
    }
}