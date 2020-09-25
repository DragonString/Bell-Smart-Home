package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 모드 자료형
 * (디지털, 아날로그)
 */
@AllArgsConstructor
@Getter
public enum ItemModeRule
{
	ERROR("ERROR", -1),
	
	DIGITAL("DIGITAL", 0),
	ANALOG("ANALOG", 1);

	private String value;
    private Integer code;
    
    public static ItemModeRule ofLegacyCode(Integer legacyCode)
    {
    	for (ItemModeRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
