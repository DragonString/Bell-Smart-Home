package net.softbell.bsh.dto.view.admin;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.PinModeRule;
import net.softbell.bsh.domain.PinTypeRule;
import net.softbell.bsh.domain.entity.NodeItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리 수정뷰 아이템 카드정보 DTO
 */
@Getter
@Setter
public class NodeManageItemCardDto
{
	private long itemId;
	private String alias;
	private String pinName;
	private PinTypeRule pinType;
	private PinModeRule pinMode;
	private byte controlMode;
	
	public NodeManageItemCardDto(NodeItem entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.itemId = entity.getItemId();
		this.alias = entity.getAlias();
		this.pinName = entity.getPinName();
		this.pinType = entity.getPinType();
		this.pinMode = entity.getPinMode();
		this.controlMode = entity.getControlMode();
	}
}