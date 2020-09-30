package net.softbell.bsh.dto.response

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : API 수행 결과 단건 DTO
 */
class SingleResultDto<T> : ResultDto() {
    var data: T? = null
}