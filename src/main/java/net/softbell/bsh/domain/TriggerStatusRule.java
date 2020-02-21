package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 상태 자료형
 */
@AllArgsConstructor
@Getter
public enum TriggerStatusRule
{
	ALL("ALL", 0), // 전체 0x0
	ERROR("ERROR", 1), // 에러시 0x1
    OCCUR("OCCUR", 2), // 트리거 발생 0x10
    RESTORE("RESTORE", 4), // 트리거 복구 0x100
    OCCUR_AND_RESTORE("OCCUR_AND_RESTORE", 6); // 트리거 발생 및 복구 0x110

	private String value;
    private Integer code;
    
    public static TriggerStatusRule ofLegacyCode(Integer legacyCode)
    {
    	for (TriggerStatusRule authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
