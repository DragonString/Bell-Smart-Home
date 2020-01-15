package net.softbell.bsh.iot.dto.bshp.v1;

import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : Bell Smart Home Protocol v1 DTO
 */
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BaseV1Dto
{
	@NonNull
	private String sender;
	@NonNull
	private String target;
	@NonNull
	private String cmd;
	@NonNull
	private String type;
	@NonNull
	private String obj;
	@Null
	private Object value;
}
