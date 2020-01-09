package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 상태 자료형
 */
@AllArgsConstructor
@Getter
public enum BanRule
{
	NORMAL("NORMAL", 0),
    PERMANENT("PERMANENT", -1),
    TEMP("TEMP", 1);

	private String value;
    private Integer code;
    
    public static BanRule ofLegacyCode(Integer legacyCode)
    {
    	for (BanRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
