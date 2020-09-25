package net.softbell.bsh.dto.request

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 정보 DTO
 */
data class CenterSettingDto (
        val isEnabled: Byte = 0,
        val iotAction: Byte = 0,
        val iotControl: Byte = 0,
        val iotMonitor: Byte = 0,
        val iotNode: Byte = 0,
        val iotReserv: Byte = 0,
        val iotTrigger: Byte = 0,
        val webAuthMode: Byte = 0,
        val webLoginFailBanTime: Int = 0,
        val webLoginFailCheckTime: Int = 0,
        val webLoginFailMaxCount: Byte = 0,
        val webMaintenance: Byte = 0,
        val webRegister: Byte = 0
)