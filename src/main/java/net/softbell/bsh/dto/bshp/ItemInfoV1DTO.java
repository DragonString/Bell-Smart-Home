package net.softbell.bsh.dto.bshp;

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
public class ItemInfoV1DTO
{
	private Byte controlMode;
	private Byte pinId;
	private Byte pinMode;
	private Byte pinType;
	private String pinName;
}