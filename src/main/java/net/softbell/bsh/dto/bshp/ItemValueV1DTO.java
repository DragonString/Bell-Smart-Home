package net.softbell.bsh.dto.bshp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Item Value DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemValueV1DTO
{
	private byte pinId;
	private short pinStatus;
}
