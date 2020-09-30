package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.entity.CenterSetting

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 카드정보 DTO
 */
class CenterSettingSummaryCardDto(entity: CenterSetting?) {
    var isEnabled: Byte
    var iotAction: Byte?
    var iotControl: Byte?
    var iotMonitor: Byte?
    var iotNode: Byte?
    var iotReserv: Byte?
    var iotTrigger: Byte?
    var webAuthMode: Byte?
    var webLoginFailBanTime: Int?
    var webLoginFailCheckTime: Int?
    var webLoginFailMaxCount: Byte?
    var webMaintenance: Byte?
    var webRegister: Byte?

    init {
        // Exception
        entity.let {
            // Convert
            isEnabled = entity!!.isEnabled!!
            iotAction = entity!!.iotAction
            iotControl = entity.iotControl
            iotMonitor = entity.iotMonitor
            iotNode = entity.iotNode
            iotReserv = entity.iotReserv
            iotTrigger = entity.iotTrigger
            webAuthMode = entity.webAuthMode
            webLoginFailBanTime = entity.webLoginFailBanTime
            webLoginFailCheckTime = entity.webLoginFailCheckTime
            webLoginFailMaxCount = entity.webLoginFailMaxCount
            webMaintenance = entity.webMaintenance
            webRegister = entity.webRegister
        }
    }
}