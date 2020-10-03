package net.softbell.bsh.dto.response

/**
 * @author : Bell(bell@softbell.net)
 * @description : API 수행 결과 단건 DTO
 */
class SingleResultDto<T>(
        var data: T
) : ResultDto() {
}