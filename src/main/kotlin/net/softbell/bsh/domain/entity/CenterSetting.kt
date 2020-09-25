package net.softbell.bsh.domain.entity

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.io.Serializable
import javax.persistence.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "center_setting")
@NamedQuery(name = "CenterSetting.findAll", query = "SELECT c FROM CenterSetting c")
class CenterSetting : Serializable {
    @Id
    @Column(name = "is_enabled", unique = true, nullable = false)
    private val isEnabled: Byte? = null

    @Column(name = "iot_action", nullable = false)
    private val iotAction: Byte? = null

    @Column(name = "iot_control", nullable = false)
    private val iotControl: Byte? = null

    @Column(name = "iot_monitor", nullable = false)
    private val iotMonitor: Byte? = null

    @Column(name = "iot_node", nullable = false)
    private val iotNode: Byte? = null

    @Column(name = "iot_reserv", nullable = false)
    private val iotReserv: Byte? = null

    @Column(name = "iot_trigger", nullable = false)
    private val iotTrigger: Byte? = null

    @Column(name = "web_auth_mode", nullable = false)
    private val webAuthMode: Byte? = null

    @Column(name = "web_login_fail_ban_time", nullable = false)
    private val webLoginFailBanTime: Int? = null

    @Column(name = "web_login_fail_check_time", nullable = false)
    private val webLoginFailCheckTime: Int? = null

    @Column(name = "web_login_fail_max_count", nullable = false)
    private val webLoginFailMaxCount: Byte? = null

    @Column(name = "web_maintenance", nullable = false)
    private val webMaintenance: Byte? = null

    @Column(name = "web_register", nullable = false)
    private val webRegister: Byte? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}