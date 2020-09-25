package net.softbell.bsh.dto.view.admin;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.CenterSetting;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 센터 설정 카드정보 DTO
 */
@Getter
@Setter
public class CenterSettingSummaryCardDto
{
	private Byte isEnabled;
	private Byte iotAction;
	private Byte iotControl;
	private Byte iotMonitor;
	private Byte iotNode;
	private Byte iotReserv;
	private Byte iotTrigger;
	private Byte webAuthMode;
	private Integer webLoginFailBanTime;
	private Integer webLoginFailCheckTime;
	private Byte webLoginFailMaxCount;
	private Byte webMaintenance;
	private Byte webRegister;
	
	public CenterSettingSummaryCardDto(CenterSetting entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.isEnabled = entity.getIsEnabled();
		this.iotAction = entity.getIotAction();
		this.iotControl = entity.getIotControl();
		this.iotMonitor = entity.getIotMonitor();
		this.iotNode = entity.getIotNode();
		this.iotReserv = entity.getIotReserv();
		this.iotTrigger = entity.getIotTrigger();
		this.webAuthMode = entity.getWebAuthMode();
		this.webLoginFailBanTime = entity.getWebLoginFailBanTime();
		this.webLoginFailCheckTime = entity.getWebLoginFailCheckTime();
		this.webLoginFailMaxCount = entity.getWebLoginFailMaxCount();
		this.webMaintenance = entity.getWebMaintenance();
		this.webRegister = entity.getWebRegister();
	}
}