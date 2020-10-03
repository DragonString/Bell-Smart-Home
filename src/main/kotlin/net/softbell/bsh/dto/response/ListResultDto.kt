package net.softbell.bsh.dto.response

/**
 * @author : Bell(bell@softbell.net)
 * @description : API 수행 결과 리스트 DTO
 */
data class ListResultDto<T>(
        var list: MutableList<T> = ArrayList()
) : ResultDto()