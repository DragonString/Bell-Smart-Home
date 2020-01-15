package net.softbell.bsh.iot.dto.bshp.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Item Value DTO
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemValueV1Dto
{
	private byte pinId;
	private short pinStatus;
}
