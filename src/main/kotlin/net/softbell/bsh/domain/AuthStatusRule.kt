package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 상태 자료형
 */
@AllArgsConstructor
@Getter
public enum AuthStatusRule
{
	SUCCESS("SUCCESS", 0), // 정상 인증
    FAIL("FAIL", 1), // 인증 실패
    ERROR("ERROR", -1); // 에러

	private String value;
    private Integer code;
    
    public static AuthStatusRule ofLegacyCode(Integer legacyCode)
    {
    	for (AuthStatusRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
