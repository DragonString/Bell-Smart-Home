package net.softbell.bsh.dto.view.admin

import net.softbell.bsh.domain.entity.CenterSetting

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 카드정보 DTO
 */
class CenterSettingSummaryCardDto(entity: CenterSetting?) {
    private val isEnabled: Byte
    private val iotAction: Byte
    private val iotControl: Byte
    private val iotMonitor: Byte
    private val iotNode: Byte
    private val iotReserv: Byte
    private val iotTrigger: Byte
    private val webAuthMode: Byte
    private val webLoginFailBanTime: Int
    private val webLoginFailCheckTime: Int
    private val webLoginFailMaxCount: Byte
    private val webMaintenance: Byte
    private val webRegister: Byte

    init {
        // Exception
        if (entity == null) return

        // Convert
        isEnabled = entity.getIsEnabled()
        iotAction = entity.getIotAction()
        iotControl = entity.getIotControl()
        iotMonitor = entity.getIotMonitor()
        iotNode = entity.getIotNode()
        iotReserv = entity.getIotReserv()
        iotTrigger = entity.getIotTrigger()
        webAuthMode = entity.getWebAuthMode()
        webLoginFailBanTime = entity.getWebLoginFailBanTime()
        webLoginFailCheckTime = entity.getWebLoginFailCheckTime()
        webLoginFailMaxCount = entity.getWebLoginFailMaxCount()
        webMaintenance = entity.getWebMaintenance()
        webRegister = entity.getWebRegister()
    }
}