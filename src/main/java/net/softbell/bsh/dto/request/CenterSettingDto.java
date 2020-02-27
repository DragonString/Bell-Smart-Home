package net.softbell.bsh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 정보 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CenterSettingDto
{
	private byte isEnabled;
	private byte iotAction;
	private byte iotControl;
	private byte iotMonitor;
	private byte iotNode;
	private byte iotReserv;
	private byte iotTrigger;
	private byte webAuthMode;
	private int webLoginFailBanTime;
	private int webLoginFailCheckTime;
	private byte webLoginFailMaxCount;
	private byte webMaintenance;
	private byte webRegister;
}