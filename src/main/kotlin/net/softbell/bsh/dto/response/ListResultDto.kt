package net.softbell.bsh.dto.response

import lombok.Getter
import lombok.Setter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : API 수행 결과 리스트 DTO
 */
@Getter
@Setter
class ListResultDto<T> : ResultDto() {
    private val list: List<T>? = null
}