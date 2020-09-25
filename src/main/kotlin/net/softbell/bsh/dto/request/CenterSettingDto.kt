package net.softbell.bsh.dto.request

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 정보 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class CenterSettingDto {
    private val isEnabled: Byte = 0
    private val iotAction: Byte = 0
    private val iotControl: Byte = 0
    private val iotMonitor: Byte = 0
    private val iotNode: Byte = 0
    private val iotReserv: Byte = 0
    private val iotTrigger: Byte = 0
    private val webAuthMode: Byte = 0
    private val webLoginFailBanTime = 0
    private val webLoginFailCheckTime = 0
    private val webLoginFailMaxCount: Byte = 0
    private val webMaintenance: Byte = 0
    private val webRegister: Byte = 0
}