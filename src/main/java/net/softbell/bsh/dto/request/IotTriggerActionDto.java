package net.softbell.bsh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 이벤트 액션 DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IotTriggerActionDto
{
	private boolean eventError;
	private boolean eventOccur;
	private boolean eventRestore;
}