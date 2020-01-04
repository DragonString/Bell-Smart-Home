package net.softbell.bsh.dto.iot;

import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BSHPv1DTO {
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
	@Null
	private Object values;
}
