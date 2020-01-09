package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 마지막 상태 자료형
 */
@AllArgsConstructor
@Getter
public enum TriggerLastStatusRule
{
	WAIT("WAIT", 0), // 최초 등록 후 대기
	OCCUR("OCCUR", 1), // 트리거 발동
	RESTORE("RESTORE", 2), // 트리거 복구
    ERROR("ERROR", -1); // 에러

	private String value;
    private Integer code;
    
    public static TriggerLastStatusRule ofLegacyCode(Integer legacyCode)
    {
    	for (TriggerLastStatusRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
