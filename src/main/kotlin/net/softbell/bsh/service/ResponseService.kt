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
        SUCCESS {
            override val code: Int
                get() = 0
            override val msg: String
                get() = "성공"
        };

        abstract val code: Int
        abstract val msg: String
    }

    // 단일건 결과를 처리하는 메소드
    fun <T> getSingleResult(data: T): SingleResultDto<T>? {
        val result = SingleResultDto<T>()
        result.data = data
        setSuccessResult(result)
        return result
    }

    // 다중건 결과를 처리하는 메소드
    fun <T> getListResult(list: MutableList<T>?): ListResultDto<T>? {
        val result = ListResultDto<T>()
        result.list = list
        setSuccessResult(result)
        return result
    }

    // 성공 결과만 처리하는 메소드
    fun getSuccessResult(): ResultDto? {
        val result = ResultDto()
        setSuccessResult(result)
        return result
    }

    // 실패 결과만 처리하는 메소드
    fun getFailResult(code: Int, msg: String?): ResultDto? {
        val result = ResultDto()
        result.success = false
        result.code = code
        result.message = msg
        return result
    }

    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private fun setSuccessResult(result: ResultDto) {
        result.success = true
        result.code = CommonResponse.SUCCESS.code
        result.message = CommonResponse.SUCCESS.msg
    }
}