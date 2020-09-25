package net.softbell.bsh.domain.entity

import java.io.Serializable
import javax.persistence.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 엔티티
 */
@Entity
@Table(name = "center_setting")
@NamedQuery(name = "CenterSetting.findAll", query = "SELECT c FROM CenterSetting c")
class CenterSetting : Serializable {
    @Id
    @Column(name = "is_enabled", unique = true, nullable = false)
    var isEnabled: Byte? = null

    @Column(name = "iot_action", nullable = false)
    var iotAction: Byte? = null

    @Column(name = "iot_control", nullable = false)
    var iotControl: Byte? = null

    @Column(name = "iot_monitor", nullable = false)
    var iotMonitor: Byte? = null

    @Column(name = "iot_node", nullable = false)
    var iotNode: Byte? = null

    @Column(name = "iot_reserv", nullable = false)
    var iotReserv: Byte? = null

    @Column(name = "iot_trigger", nullable = false)
    var iotTrigger: Byte? = null

    @Column(name = "web_auth_mode", nullable = false)
    var webAuthMode: Byte? = null

    @Column(name = "web_login_fail_ban_time", nullable = false)
    var webLoginFailBanTime: Int? = null

    @Column(name = "web_login_fail_check_time", nullable = false)
    var webLoginFailCheckTime: Int? = null

    @Column(name = "web_login_fail_max_count", nullable = false)
    var webLoginFailMaxCount: Byte? = null

    @Column(name = "web_maintenance", nullable = false)
    var webMaintenance: Byte? = null

    @Column(name = "web_register", nullable = false)
    var webRegister: Byte? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}