package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 타입 자료형
 * (버튼, 전등, 액티브부저, 패시브부저, 스피커, 온도센서, 습도센서, 문열림센서, 소리감지센서, 적외선센서, 노크센서, 공기질센서, 가스센서, RFID리더)
 * 0~999: 유저 카테고리
 * 1000~1999: 제어 카테고리
 * 2000~2999: 센서 카테고리
 * 3000~3999: 리더 카테고리
 */
@AllArgsConstructor
@Getter
public enum ItemTypeRule
{
	ERROR							("ERROR", -1),
	
	// ### USER REGION START ###
	USER_BUTTON						("USER_BUTTON", 0),
	USER_SWITCH						("USER_SWITCH", 1),
	// ### USER REGION END ###
	
	// ### CONTROL REGION START ###
	// ## LED CONTROL REGION START ##
	CONTROL_INDICATOR_LED			("CONTROL_INDICATOR_LED", 1000),
    CONTROL_LIGHT					("CONTROL_LIGHT", 1001),
    // ## LED CONTROL REGION END ##

    // ## ACTIVE CONTROL REGION START ##
    CONTROL_DOOR					("CONTROL_DOOR", 1100),
    CONTROL_CURTAIN					("CONTROL_CURTAIN", 1101),
    // ## ACTIVE CONTROL REGION END ##
    
    // ## SOUND CONTROL REGION START ##
    CONTROL_ACTIVE_BUZZER			("CONTROL_ACTIVE_BUZZER", 1900),
    CONTROL_PASSIVE_BUZZER			("CONTROL_PASSIVE_BUZZER", 1901),
    CONTROL_SPEAKER					("CONTROL_SPEAKER", 1902),
    // ## SOUND CONTROL REGION END ##
    // ### CONTROL REGION END ###
    
    // ### SENSOR REGION START ###
    // ## MEASUREMENT SENSOR REGION START ##
    SENSOR_HUMIDITY					("SENSOR_HUMIDITY", 2000),
    SENSOR_TEMPERATURE				("SENSOR_TEMPERATURE", 2001),
    SENSOR_AIR_QUALITY				("SENSOR_AIR_QUALITY", 2010),
    SENSOR_GAS						("SENSOR_GAS", 2011),
    SENSOR_SOUND					("SENSOR_SOUND", 2090),
    // ## MEASUREMENT SENSOR REGION END ##
    
    // ## STATUS SENSOR REGION START ##
    SENSOR_DOOR						("SENSOR_DOOR", 2100),
    SENSOR_IR						("SENSOR_IR", 2101),
    SENSOR_KNOCK					("SENSOR_KNOCK", 2102),
    // ## STATUS SENSOR REGION END ##
	// ### SENSOR REGION END ###
	
	// ### READER REGION START ###
	READER_RFID						("READER_RFID", 3000),
	READER_FINGERPRINT				("READER_FINGERPRINT", 3001);
	// ### READER REGION END ###

	private String value;
    private Integer code;
    
    public static ItemTypeRule ofLegacyCode(Integer legacyCode)
    {
    	for (ItemTypeRule authStatusRule : values())
            if (authStatusRule.getCode().equals(legacyCode))
                return authStatusRule;
    	
    	return null;
    }
}
