package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 핀 모드 자료형
 */
@AllArgsConstructor
@Getter
public enum PinModeRule
{
	INPUT("INPUT", 0), // pinMode(INPUT)
    OUTPUT("OUTPUT", 1), // pinMode(OUTPUT)
    ERROR("ERROR", -1);

	private String value;
    private Integer code;
    
    public static PinModeRule ofLegacyCode(Integer legacyCode)
    {
    	for (PinModeRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
