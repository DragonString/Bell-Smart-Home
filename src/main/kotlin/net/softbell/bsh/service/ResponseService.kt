package net.softbell.bsh.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.softbell.bsh.dto.response.ListResultDto;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.dto.response.SingleResultDto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 응답 메시지 처리 서비스
 */
@Service
public class ResponseService
{
	@AllArgsConstructor
    @Getter
    public enum CommonResponse
    {
        SUCCESS(0, "성공");

        int code;
        String msg;
    }
	
    // 단일건 결과를 처리하는 메소드
    public <T> SingleResultDto<T> getSingleResult(T data)
    {
        SingleResultDto<T> result = new SingleResultDto<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }
    
    // 다중건 결과를 처리하는 메소드
    public <T> ListResultDto<T> getListResult(List<T> list)
    {
        ListResultDto<T> result = new ListResultDto<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    
    // 성공 결과만 처리하는 메소드
    public ResultDto getSuccessResult()
    {
    	ResultDto result = new ResultDto();
        setSuccessResult(result);
        return result;
    }
    
    // 실패 결과만 처리하는 메소드
    public ResultDto getFailResult(int code, String msg)
    {
    	ResultDto result = new ResultDto();
        result.setSuccess(false);
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }
    
    // 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(ResultDto result)
    {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMessage(CommonResponse.SUCCESS.getMsg());
    }
}
