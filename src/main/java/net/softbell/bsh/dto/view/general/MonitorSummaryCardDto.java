package net.softbell.bsh.dto.view.general;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터뷰 카드정보 DTO
 */
@Getter
@Setter
public class MonitorSummaryCardDto
{
	private String alias;
	private List<MonitorCardItemDto> listItems;
	private byte controlMode;
	// TODO 마지막 연결 시각
	
	public MonitorSummaryCardDto(Node entity)
	{
		// Exception
		if (entity == null)
			return;
		
		// Init
		listItems = new ArrayList<MonitorCardItemDto>();
		
		// Convert
		this.alias = entity.getAlias();
		this.controlMode = entity.getControlMode();
		for (NodeItem nodeItem :  entity.getNodeItems())
			listItems.add(new MonitorCardItemDto(nodeItem));
	}
	
	@Getter
	@Setter
	public class MonitorCardItemDto
	{
		private String alias;
		private long lastStatus;
		private int widthPercent;
		
		public MonitorCardItemDto(NodeItem entity)
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
			this.alias = entity.getAlias();
			this.lastStatus = lastHistory.getItemStatus();
			switch (entity.getItemMode())
			{
				case DIGITAL:
					if (this.lastStatus == 0)
						this.widthPercent = 0;
					else
						this.widthPercent = 100;
					
					break;
					
				case ANALOG:
					this.widthPercent = (int) (this.lastStatus / 10);
						
					break;
					
				default:
					this.widthPercent = 0;
					
					break;
			}
		}
	}
}