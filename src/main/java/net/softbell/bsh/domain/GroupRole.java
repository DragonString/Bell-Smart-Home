package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 상태 자료형
 */
@AllArgsConstructor
@Getter
public enum GroupRole
{
    REJECT("REJECT", -1), // 제한
	READ_ONLY("READ_ONLY", 0), // 상태 읽기만 가능
	MANUAL_CONTROL("MANUAL_CONTROL", 1), // 컨트롤 패널에서 수동 조작만 가능
	ACTION("ACTION", 2), // 액션 이용 가능
    RESERV("RESERV", 3), // 예약기능 이용 가능
    TRIGGER("TRIGGER", 4); // 트리거 이용 가능

	private String value;
    private Integer code;
    
    public static GroupRole ofLegacyCode(Integer legacyCode)
    {
    	for (GroupRole authStatusRule : values())
            if (authStatusRule.getCode() == legacyCode)
                return authStatusRule;
    	
    	return null;
    }
}
