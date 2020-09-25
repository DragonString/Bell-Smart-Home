package net.softbell.bsh.service

import net.softbell.bsh.dto.response.ListResultDto
import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.dto.response.SingleResultDto
import org.springframework.stereotype.Service

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 응답 메시지 처리 서비스
 */
@Service
class ResponseService {
    enum class CommonResponse {
        SUCCESS(0, "성공");

        var code = 0
        var msg: String? = null
    }

    // 단일건 결과를 처리하는 메소드
    fun <T> getSingleResult(data: T): SingleResultDto<T> {
        val result = SingleResultDto<T>()
        result.setData(data)
        successResult = result
        return result
    }

    // 다중건 결과를 처리하는 메소드
    fun <T> getListResult(list: List<T>?): ListResultDto<T> {
        val result = ListResultDto<T>()
        result.setList(list)
        successResult = result
        return result
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    // 성공 결과만 처리하는 메소드
    var successResult: ResultDto
        get() {
            val result = ResultDto()
            successResult = result
            return result
        }
        private set(result) {
            result.setSuccess(true)
            result.setCode(CommonResponse.SUCCESS.getCode())
            result.setMessage(CommonResponse.SUCCESS.getMsg())
        }

    // 실패 결과만 처리하는 메소드
    fun getFailResult(code: Int, msg: String?): ResultDto {
        val result = ResultDto()
        result.setSuccess(false)
        result.setCode(code)
        result.setMessage(msg)
        return result
    }
}