package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 카테고리 자료형
 * (사용자, 제어, 센서, 리더)
 */
@AllArgsConstructor
@Getter
public enum ItemCategoryRule
{
	ERROR("ERROR", -1),
	USER("USER", 0),
    CONTROL("CONTROL", 10),
    SENSOR("SENSOR", 20),
    READER("READER", 30);

	private String value;
    private Integer code;
    
    public static ItemCategoryRule ofLegacyCode(Integer legacyCode)
    {
    	for (ItemCategoryRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
