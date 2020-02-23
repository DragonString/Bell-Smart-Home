package net.softbell.bsh.dto.view.admin;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeGroup;
import net.softbell.bsh.domain.entity.NodeGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹뷰 요약 카드정보 DTO
 */
@Getter
@Setter
public class NodeGroupSummaryCardDto
{
	private Long gid;
	private String name;
	private EnableStatusRule enableStatus;
	private List<NodeGroupItemDto> listNode;
	
	public NodeGroupSummaryCardDto(NodeGroup entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		listNode = new ArrayList<NodeGroupItemDto>();
		
		// Convert
		this.gid = entity.getNodeGroupId();
		this.name = entity.getName();
		this.enableStatus = entity.getEnableStatus();
		for (NodeGroupItem child : entity.getNodeGroupItems())
			listNode.add(new NodeGroupItemDto(child.getNode()));
	}
	
	@Getter
	@Setter
	public class NodeGroupItemDto
	{
		private Long nodeId;
		private String alias;
		
		public NodeGroupItemDto(Node entity)
		{
			this.nodeId = entity.getNodeId();
			this.alias = entity.getAlias();
		}
	}
}