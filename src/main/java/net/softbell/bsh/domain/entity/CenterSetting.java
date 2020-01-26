package net.softbell.bsh.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
@Table(name="center_setting")
@NamedQuery(name="CenterSetting.findAll", query="SELECT c FROM CenterSetting c")
public class CenterSetting implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="is_enabled", unique=true, nullable=false)
	private byte isEnabled;

	@Column(name="iot_action", nullable=false)
	private byte iotAction;

	@Column(name="iot_control", nullable=false)
	private byte iotControl;

	@Column(name="iot_monitor", nullable=false)
	private byte iotMonitor;

	@Column(name="iot_node", nullable=false)
	private byte iotNode;

	@Column(name="iot_reserv", nullable=false)
	private byte iotReserv;

	@Column(name="iot_trigger", nullable=false)
	private byte iotTrigger;

	@Column(name="web_auth_mode", nullable=false)
	private byte webAuthMode;

	@Column(name="web_login_fail_ban_time", nullable=false)
	private int webLoginFailBanTime;

	@Column(name="web_login_fail_check_time", nullable=false)
	private int webLoginFailCheckTime;

	@Column(name="web_login_fail_max_count", nullable=false)
	private byte webLoginFailMaxCount;

	@Column(name="web_maintenance", nullable=false)
	private byte webMaintenance;

	@Column(name="web_register", nullable=false)
	private byte webRegister;
}