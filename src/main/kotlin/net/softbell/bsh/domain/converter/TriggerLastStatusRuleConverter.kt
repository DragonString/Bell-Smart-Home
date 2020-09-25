package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.EnableStatusRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 마지막 상태 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class TriggerLastStatusRuleConverter : AttributeConverter<EnableStatusRule?, Int?> {
    override fun convertToDatabaseColumn(enableStatusRule: EnableStatusRule?): Int? {
        return enableStatusRule?.getCode()
    }

    override fun convertToEntityAttribute(code: Int?): EnableStatusRule? {
        return EnableStatusRule.Companion.ofLegacyCode(code)
    }
}