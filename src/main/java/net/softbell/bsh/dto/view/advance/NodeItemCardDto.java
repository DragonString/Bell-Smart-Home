package net.softbell.bsh.dto.view.advance;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.ItemCategoryRule;
import net.softbell.bsh.domain.ItemTypeRule;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 수정뷰 아이템 카드정보 DTO
 */
@Getter
@Setter
public class NodeItemCardDto
{
	private long itemId;
	private String alias;
	private String itemName;
	private ItemTypeRule itemType;
	private ItemCategoryRule itemCategory;
	private byte controlMode;
	private long lastStatus;
	
	public NodeItemCardDto(NodeItem entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Field
		List<NodeItemHistory> listHistory;
		NodeItemHistory lastHistory;
		
		// Init
		listHistory = entity.getNodeItemHistories();
		lastHistory = listHistory.get(listHistory.size() - 1);
		
		// Convert
		this.itemId = entity.getItemId();
		this.alias = entity.getAlias();
		this.itemName = entity.getItemName();
		this.itemType = entity.getItemType();
		this.itemCategory = entity.getItemCategory();
		this.controlMode = entity.getControlMode();
		this.lastStatus = lastHistory.getItemStatus();
	}
}