package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 센터 설정 정보 DTO
 */
data class CenterSettingDto (
        var isEnabled: Byte = 0,
        var iotAction: Byte = 0,
        var iotControl: Byte = 0,
        var iotMonitor: Byte = 0,
        var iotNode: Byte = 0,
        var iotReserv: Byte = 0,
        var iotTrigger: Byte = 0,
        var webAuthMode: Byte = 0,
        var webLoginFailBanTime: Int,
        var webLoginFailCheckTime: Int,
        var webLoginFailMaxCount: Byte,
        var webMaintenance: Byte = 0,
        var webRegister: Byte = 0
)