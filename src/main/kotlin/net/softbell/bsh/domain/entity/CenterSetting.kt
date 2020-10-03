package net.softbell.bsh.domain.entity

import java.io.Serializable
import javax.persistence.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 센터 설정 엔티티
 */
@Entity
@Table(name = "center_setting")
@NamedQuery(name = "CenterSetting.findAll", query = "SELECT c FROM CenterSetting c")
class CenterSetting(
        @Column(name = "is_enabled", unique = true, nullable = false)
        var isEnabled: Byte,

        @Column(name = "iot_action", nullable = false)
        var iotAction: Byte,

        @Column(name = "iot_control", nullable = false)
        var iotControl: Byte,

        @Column(name = "iot_monitor", nullable = false)
        var iotMonitor: Byte,

        @Column(name = "iot_node", nullable = false)
        var iotNode: Byte,

        @Column(name = "iot_reserv", nullable = false)
        var iotReserv: Byte,

        @Column(name = "iot_trigger", nullable = false)
        var iotTrigger: Byte,

        @Column(name = "web_auth_mode", nullable = false)
        var webAuthMode: Byte,

        @Column(name = "web_login_fail_ban_time", nullable = false)
        var webLoginFailBanTime: Int,

        @Column(name = "web_login_fail_check_time", nullable = false)
        var webLoginFailCheckTime: Int,

        @Column(name = "web_login_fail_max_count", nullable = false)
        var webLoginFailMaxCount: Byte,

        @Column(name = "web_maintenance", nullable = false)
        var webMaintenance: Byte,

        @Column(name = "web_register", nullable = false)
        var webRegister: Byte
) : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    var id: Long = 0

    companion object {
        private const val serialVersionUID = 1L
    }
}