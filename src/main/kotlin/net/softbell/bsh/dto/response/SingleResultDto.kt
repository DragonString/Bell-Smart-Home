package net.softbell.bsh.dto.response

import lombok.Getter
import lombok.Setter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : API 수행 결과 단건 DTO
 */
@Getter
@Setter
class SingleResultDto<T> : ResultDto() {
    private val data: T? = null
}