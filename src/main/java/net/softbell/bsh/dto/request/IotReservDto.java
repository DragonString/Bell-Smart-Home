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
 * @Description : 예약정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IotReservDto
{
    private boolean enableStatus;
    private String description;
    private String expression;
    private HashMap<Long, IotActionDto> mapAction;
}