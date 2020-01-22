package net.softbell.bsh.dto.view.admin;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.CenterSetting;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리뷰 리스트 카드정보 DTO
 */
@Getter
@Setter
public class CenterSettingSummaryCardDto
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