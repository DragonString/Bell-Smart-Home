package net.softbell.bsh.dto.view.admin;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.Node;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 노드 카드 아이템 DTO
 */
@Getter
@Setter
public class GroupNodeCardItemDto
{
	private Long nodeId;
	private String nodeName;
	private String alias;
	private String version;
	
	public GroupNodeCardItemDto(Node entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.nodeId = entity.getNodeId();
		this.nodeName = entity.getNodeName();
		this.alias = entity.getAlias();
		this.version = entity.getVersion();
	}
}