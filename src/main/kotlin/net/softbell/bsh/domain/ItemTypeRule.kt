package net.softbell.bsh.domain

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 타입 자료형
 * (버튼, 전등, 액티브부저, 패시브부저, 스피커, 온도센서, 습도센서, 문열림센서, 소리감지센서, 적외선센서, 노크센서, 공기질센서, 가스센서, RFID리더)
 * 0~999: 유저 카테고리
 * 1000~1999: 제어 카테고리
 * 2000~2999: 센서 카테고리
 * 3000~3999: 리더 카테고리
 */
enum class ItemTypeRule {
    UNKNOWN { // 미확인
        override val value: String
            get() = "UNKNOWN"
        override val code: Int
            get() = -1
    },

    // ### USER REGION START ###
    USER_BUTTON {
        override val value: String
            get() = "USER_BUTTON"
        override val code: Int
            get() = 0
    },
    USER_SWITCH {
        override val value: String
            get() = "USER_SWITCH"
        override val code: Int
            get() = 1
    },
    // ### USER REGION END ###

    // ### CONTROL REGION START ###
    // ## LED CONTROL REGION START ##
    CONTROL_INDICATOR_LED {
        override val value: String
            get() = "CONTROL_INDICATOR_LED"
        override val code: Int
            get() = 1000
    },
    CONTROL_LED {
        override val value: String
            get() = "CONTROL_LED"
        override val code: Int
            get() = 1001
    },
    CONTROL_LIGHT {
        override val value: String
            get() = "CONTROL_LIGHT"
        override val code: Int
            get() = 1002
    },
    // ## LED CONTROL REGION END ##

    // ## ACTIVE CONTROL REGION START ##
    CONTROL_DOOR {
        override val value: String
            get() = "CONTROL_DOOR"
        override val code: Int
            get() = 1100
    },
    CONTROL_CURTAIN {
        override val value: String
            get() = "CONTROL_CURTAIN"
        override val code: Int
            get() = 1101
    },
    // ## ACTIVE CONTROL REGION END ##

    // ## SOUND CONTROL REGION START ##
    CONTROL_ACTIVE_BUZZER {
        override val value: String
            get() = "CONTROL_ACTIVE_BUZZER"
        override val code: Int
            get() = 1900
    },
    CONTROL_PASSIVE_BUZZER {
        override val value: String
            get() = "CONTROL_PASSIVE_BUZZER"
        override val code: Int
            get() = 1901
    },
    CONTROL_SPEAKER {
        override val value: String
            get() = "CONTROL_SPEAKER"
        override val code: Int
            get() = 1902
    },
    // ## SOUND CONTROL REGION END ##
    // ### CONTROL REGION END ###

    // ### SENSOR REGION START ###
    // ## MEASUREMENT SENSOR REGION START ##
    SENSOR_HUMIDITY {
        override val value: String
            get() = "SENSOR_HUMIDITY"
        override val code: Int
            get() = 2000
    },
    SENSOR_TEMPERATURE {
        override val value: String
            get() = "SENSOR_TEMPERATURE"
        override val code: Int
            get() = 2001
    },
    SENSOR_AIR_QUALITY {
        override val value: String
            get() = "SENSOR_AIR_QUALITY"
        override val code: Int
            get() = 2010
    },
    SENSOR_GAS {
        override val value: String
            get() = "SENSOR_GAS"
        override val code: Int
            get() = 2011
    },
    SENSOR_SOUND {
        override val value: String
            get() = "SENSOR_SOUND"
        override val code: Int
            get() = 2090
    },
    // ## MEASUREMENT SENSOR REGION END ##

    // ## STATUS SENSOR REGION START ##
    SENSOR_DOOR {
        override val value: String
            get() = "SENSOR_DOOR"
        override val code: Int
            get() = 2100
    },
    SENSOR_IR {
        override val value: String
            get() = "SENSOR_IR"
        override val code: Int
            get() = 2101
    },
    SENSOR_KNOCK {
        override val value: String
            get() = "SENSOR_KNOCK"
        override val code: Int
            get() = 2102
    },
    // ## STATUS SENSOR REGION END ##
    // ### SENSOR REGION END ###

    // ### READER REGION START ###
    READER_RFID {
        override val value: String
            get() = "READER_RFID"
        override val code: Int
            get() = 3000
    },
    READER_FINGERPRINT {
        override val value: String
            get() = "READER_FINGERPRINT"
        override val code: Int
            get() = 3001
    };
    // ### READER REGION END ###

    abstract val value: String
    abstract val code: Int

    companion object {
        fun ofLegacyCode(legacyCode: Int): ItemTypeRule {
            for (rule in values())
                if (rule.code == legacyCode)
                    return rule
            return UNKNOWN
        }
    }
}