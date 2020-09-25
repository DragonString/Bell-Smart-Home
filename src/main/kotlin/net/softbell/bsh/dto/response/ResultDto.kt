package net.softbell.bsh.dto.response

import io.swagger.annotations.ApiModelProperty

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : API 수행 결과 DTO
 */
open class ResultDto {
    @ApiModelProperty(value = "응답 성공여부")
    private val success = false

    @ApiModelProperty(value = "응답 코드")
    private val code = 0

    @ApiModelProperty(value = "응답 메시지")
    private val message: String? = null
}