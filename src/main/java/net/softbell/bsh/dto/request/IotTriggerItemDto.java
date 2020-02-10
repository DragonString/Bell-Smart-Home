package net.softbell.bsh.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 아이템 정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IotTriggerItemDto
{
	private Long itemId;
    private String alias;
}