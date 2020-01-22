package net.softbell.bsh.dto.view.admin;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 관리뷰 리스트 카드정보 DTO
 */
@Getter
@Setter
public class NodeManageSummaryCardDto
{
	private long nodeId;
	private String uid;
	private String alias;
	private EnableStatusRule enableStatus;
	
	public NodeManageSummaryCardDto(Node entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.nodeId = entity.getNodeId();
		this.uid = entity.getUid();
		this.alias = entity.getAlias();
		this.enableStatus = entity.getEnableStatus();
	}
}