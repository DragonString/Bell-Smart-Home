package net.softbell.bsh.dto.view.general;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeActionItem;
import net.softbell.bsh.domain.entity.NodeItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 액션 카드정보 DTO
 */
@Getter
@Setter
public class ActionItemCardDto
{
	private long itemId;
	private long nodeId;
	private String nodeAlias;
	private String itemAlias;
	private long pinStatus;
	private long pinMin, pinMax;
	
	public ActionItemCardDto(NodeItem entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.itemId = entity.getItemId();
		this.nodeId = entity.getNode().getNodeId();
		this.nodeAlias = entity.getNode().getAlias();
		this.itemAlias = entity.getAlias();
		this.pinMin = 0;
		this.pinMax = 1024; // TODO 핀 타입에 따라 맥스값 지정해주기
	}
	
	public ActionItemCardDto(NodeActionItem entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.itemId = entity.getNodeItem().getItemId();
		this.nodeId = entity.getNodeItem().getNode().getNodeId();
		this.nodeAlias = entity.getNodeItem().getNode().getAlias();
		this.itemAlias = entity.getNodeItem().getAlias();
		this.pinStatus = entity.getItemStatus();
		this.pinMin = 0;
		this.pinMax = 1024;
	}
}