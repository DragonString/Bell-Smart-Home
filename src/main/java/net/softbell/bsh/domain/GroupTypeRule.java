package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 타입 자료형
 */
@AllArgsConstructor
@Getter
public enum GroupTypeRule
{
	DEFAULT("DEFAULT", 0), // 자동 생성
    USER("USER", 1); // 유저가 생성

	private String value;
    private Integer code;
    
    public static GroupTypeRule ofLegacyCode(Integer legacyCode)
    {
    	for (GroupTypeRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
