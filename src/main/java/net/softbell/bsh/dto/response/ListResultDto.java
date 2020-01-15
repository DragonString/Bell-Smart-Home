package net.softbell.bsh.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : API 수행 결과 리스트 DTO
 */
@Getter
@Setter
public class ListResultDto<T> extends ResultDto
{
    private List<T> list;
}