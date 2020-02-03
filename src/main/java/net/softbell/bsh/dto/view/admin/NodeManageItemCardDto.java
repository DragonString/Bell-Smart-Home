package net.softbell.bsh.dto.view.admin;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.ItemCategoryRule;
import net.softbell.bsh.domain.ItemModeRule;
import net.softbell.bsh.domain.ItemTypeRule;
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
	private String itemName;
	private ItemModeRule itemMode;
	private ItemTypeRule itemType;
	private ItemCategoryRule itemCategory;
	private byte controlMode;
	
	public NodeManageItemCardDto(NodeItem entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.itemId = entity.getItemId();
		this.alias = entity.getAlias();
		this.itemName = entity.getItemName();
		this.itemMode = entity.getItemMode();
		this.itemType = entity.getItemType();
		this.itemCategory = entity.getItemCategory();
		this.controlMode = entity.getControlMode();
	}
}