package net.softbell.bsh.dto.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 채팅 메시지 프로토콜 DTO
 * @author Bell
 * cmd: join, exit, msg
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatProtocolDTO {
	private String cmd;
	private String name;
	private String content;
	private String date;
}
