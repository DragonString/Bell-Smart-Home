package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.entity.CenterSetting

/**
 * @author : Bell(bell@softbell.net)
 * @description : 센터 설정 카드정보 DTO
 */
class CenterSettingSummaryCardDto(entity: CenterSetting) {
    val enabled: Byte = entity.isEnabled
    val iotAction: Byte = entity.iotAction
    val iotControl: Byte = entity.iotControl
    val iotMonitor: Byte = entity.iotMonitor
    val iotNode: Byte = entity.iotNode
    val iotReserv: Byte = entity.iotReserv
    val iotTrigger: Byte = entity.iotTrigger
    val webAuthMode: Byte = entity.webAuthMode
    val webLoginFailBanTime: Int = entity.webLoginFailBanTime
    val webLoginFailCheckTime: Int = entity.webLoginFailCheckTime
    val webLoginFailMaxCount: Byte = entity.webLoginFailMaxCount
    val webMaintenance: Byte = entity.webMaintenance
    val webRegister: Byte = entity.webRegister
}