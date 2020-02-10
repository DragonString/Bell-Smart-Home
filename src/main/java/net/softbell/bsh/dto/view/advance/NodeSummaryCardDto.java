package net.softbell.bsh.dto.view.advance;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드뷰 리스트 카드정보 DTO
 */
@Getter
@Setter
public class NodeSummaryCardDto
{
	private Long nodeId;
	private String alias;
	private EnableStatusRule enableStatus;
	private Byte controlMode;
	
	public NodeSummaryCardDto(Node entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.nodeId = entity.getNodeId();
		this.alias = entity.getAlias();
		this.enableStatus = entity.getEnableStatus();
		this.controlMode = entity.getControlMode();
	}
}