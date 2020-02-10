package net.softbell.bsh.dto.view.advance;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.ItemCategoryRule;
import net.softbell.bsh.domain.ItemModeRule;
import net.softbell.bsh.domain.ItemTypeRule;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 등록 및 수정뷰 노드 아이템 카드정보 DTO
 */
@Getter
@Setter
public class TriggerItemCardDto
{
	private Long nodeId;
	private String nodeAlias;
	private Long itemId;
	private String alias;
	private ItemModeRule itemMode;
	private ItemTypeRule itemType;
	private ItemCategoryRule itemCategory;
	
	public TriggerItemCardDto(NodeItem entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Field
		Node node;
		
		// Init
		node = entity.getNode();
		
		// Convert
		this.nodeId = node.getNodeId();
		this.nodeAlias = node.getAlias();
		this.itemId = entity.getItemId();
		this.alias = entity.getAlias();
		this.itemMode = entity.getItemMode();
		this.itemType = entity.getItemType();
		this.itemCategory = entity.getItemCategory();
	}
}