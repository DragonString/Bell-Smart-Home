package net.softbell.bsh.dto.request

/**
 * @author : Bell(bell@softbell.net)
 * @description : 센터 설정 정보 DTO
 */
data class CenterSettingDto (
        var isEnabled: Byte,
        var iotAction: Byte,
        var iotControl: Byte,
        var iotMonitor: Byte,
        var iotNode: Byte,
        var iotReserv: Byte,
        var iotTrigger: Byte,
        var webAuthMode: Byte,
        var webLoginFailBanTime: Int,
        var webLoginFailCheckTime: Int,
        var webLoginFailMaxCount: Byte,
        var webMaintenance: Byte,
        var webRegister: Byte
)