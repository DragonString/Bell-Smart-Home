package net.softbell.bsh.dto.bshp;

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
public class NodeInfoV1DTO
{
	private String uid;
	private String token;
	private byte controlMode;
	private String nodeName;
}
