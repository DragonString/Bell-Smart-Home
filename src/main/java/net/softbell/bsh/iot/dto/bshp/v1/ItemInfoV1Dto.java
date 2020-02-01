package net.softbell.bsh.iot.dto.bshp.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Items Info DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemInfoV1Dto
{
	private Byte controlMode;
	private Byte itemIndex;
	private Integer itemCategory;
	private Integer itemType;
	private String itemName;
}