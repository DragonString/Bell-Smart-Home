package net.softbell.bsh.dto.request;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IotActionDto
{
	private Long actionId;
    private boolean enableStatus;
    private String description;
    private HashMap<Long, IotActionItemDto> mapActionItem;
}