package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 핀 타입 자료형 (pinMode가 INPUT일때만 사용)
 */
@AllArgsConstructor
@Getter
public enum PinTypeRule
{
	DIGITAL("DIGITAL", 0), // digitalRead
    ANALOG("ANALOG", 1), // analogRead
    DISABLE("DISABLE", -1); // OUTPUT 모드일경우

	private String value;
    private Integer code;
    
    public static PinTypeRule ofLegacyCode(Integer legacyCode)
    {
    	for (PinTypeRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
