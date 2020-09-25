package net.softbell.bsh.dto.view.general;

import lombok.Getter;
import lombok.Setter;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터뷰 카드 아이템 정보 DTO
 */
@Getter
@Setter
public class MonitorCardItemDto
{
	private String alias;
	private Double lastStatus;
	private Integer widthPercent;
	private Boolean isDigital;
	
	public MonitorCardItemDto(NodeItem entity, NodeItemHistory lastHistory)
	{
		// Exception
		if (entity == null)
			return;
		
		// Convert
		this.alias = entity.getAlias();
		this.lastStatus = lastHistory.getItemStatus();
		switch (entity.getItemMode())
		{
			case DIGITAL:
				isDigital = true;
				if (this.lastStatus == 0)
					this.widthPercent = 0;
				else
					this.widthPercent = 100;
				
				break;
				
			case ANALOG:
				isDigital = false;
				switch (entity.getItemType())
				{
					case SENSOR_HUMIDITY:
						this.widthPercent = this.lastStatus.intValue(); // 습도는 0~100%
						break;
						
					case SENSOR_TEMPERATURE:
						this.widthPercent = (int) (this.lastStatus + 20); // 온도는 -20~40
						break;
						
					case READER_RFID:
						if (this.lastStatus != 0)
							this.widthPercent = 100; // RFID는 탐지 여부로 판정
						else
							this.widthPercent = 0;
						break;
						
					default:
						this.widthPercent = (int) (this.lastStatus / 10);
				}
					
				break;
				
			default:
				this.widthPercent = 0;
				
				break;
		}
	}
}