package net.softbell.bsh.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : API 수행 결과 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDto
{
	@ApiModelProperty(value = "응답 성공여부")
    private boolean success;

    @ApiModelProperty(value = "응답 코드")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
	private String message;
}
