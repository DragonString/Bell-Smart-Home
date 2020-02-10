package net.softbell.bsh.iot.dto.bshp.v1;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : BSHPv1 전용 Node Info DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NodeInfoV1Dto
{
	private String uid;
	private Byte controlMode;
	private String nodeName;
	private String version;
}
