package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 제어 모드 비트플래그 자료형
 */
@AllArgsConstructor
@Getter
public enum ControlModeRule
{
	INFO_SYNC_DISABLE("INFO_SYNC_DISABLE", 				(byte)0b00000001),
	AUTO_CONTROL_DISABLE("AUTO_CONTROL_DISABLE", 		(byte)0b00000010),
	MANUAL_CONTROL_DISABLE("MANUAL_CONTROL_DISABLE", 	(byte)0b00000100);

	private String value;
    private Byte code;
    
    public static ControlModeRule ofLegacyCode(Byte legacyCode)
    {
    	for (ControlModeRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
