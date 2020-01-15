package net.softbell.bsh.dto.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : API 수행 결과 단건 DTO
 */
@Getter
@Setter
public class SingleResultDto<T> extends ResultDto
{
    private T data;
}