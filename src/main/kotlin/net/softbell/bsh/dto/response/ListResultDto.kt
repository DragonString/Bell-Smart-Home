package net.softbell.bsh.dto.response

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : API 수행 결과 리스트 DTO
 */
class ListResultDto<T> : ResultDto() {
    private val list: List<T>? = null
}