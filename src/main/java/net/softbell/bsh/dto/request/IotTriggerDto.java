package net.softbell.bsh.dto.request;

import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IotTriggerDto
{
    private boolean enableStatus;
    private String description;
    private String expression;
    private HashMap<Long, IotTriggerItemDto> mapTriggerItem;
    private List<Long> occurActionId;
    private List<Long> restoreActionId;
}