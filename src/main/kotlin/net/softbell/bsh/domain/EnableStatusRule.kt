package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 활성화 상태 자료형
 */
@AllArgsConstructor
@Getter
public enum EnableStatusRule
{
	WAIT("WAIT", 0), // 인증 대기
	DISABLE("DISABLE", 1), // 비활성화
	ENABLE("ENABLE", 2), // 활성화
    REJECT("REJECT", -1); // 접근 제한

	private String value;
    private Integer code;
    
    public static EnableStatusRule ofLegacyCode(Integer legacyCode)
    {
    	for (EnableStatusRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
