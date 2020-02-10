package net.softbell.bsh.dto.view.advance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 아이템 히스토리뷰 카드정보 DTO
 */
@Getter
@Setter
public class NodeItemHistoryCardDto
{
	private String alias;
	private List<NodeItemHistoryCardItemDto> listItems;
	
	public NodeItemHistoryCardDto(NodeItem entity, List<NodeItemHistory> listNodeItemHistory)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		listItems = new ArrayList<NodeItemHistoryCardItemDto>();
		
		// Convert
		this.alias = entity.getAlias();
		for (NodeItemHistory nodeItem :  listNodeItemHistory)
			listItems.add(new NodeItemHistoryCardItemDto(nodeItem));
	}
	
	public NodeItemHistoryCardDto(NodeItem entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Field
		List<NodeItemHistory> listNodeItemHistory;
		int intStart, intEnd;
		
		// Init
		listItems = new ArrayList<NodeItemHistoryCardItemDto>();
		listNodeItemHistory = entity.getNodeItemHistories();
		intEnd = listNodeItemHistory.size() - 1;
		intStart = intEnd - 50;
		
		// Process
		if (intStart < 0)
			intStart = 0;
		listNodeItemHistory = listNodeItemHistory.subList(intStart, intEnd);
		
		// Convert
		this.alias = entity.getAlias();
		for (NodeItemHistory nodeItem :  listNodeItemHistory)
			listItems.add(new NodeItemHistoryCardItemDto(nodeItem));
	}
	
	@Getter
	@Setter
	public class NodeItemHistoryCardItemDto
	{
		private Date receiveDate;
		private Double pinStatus;
		
		public NodeItemHistoryCardItemDto(NodeItemHistory entity)
		{
			// Exception
			if (entity == null)
				return;
			
			// Convert
			this.receiveDate = entity.getReceiveDate();
			this.pinStatus = entity.getItemStatus();
		}
	}
}