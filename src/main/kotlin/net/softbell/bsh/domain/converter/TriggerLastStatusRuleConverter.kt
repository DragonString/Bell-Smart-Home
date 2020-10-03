package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.EnableStatusRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author : Bell(bell@softbell.net)
 * @description : 트리거 마지막 상태 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class TriggerLastStatusRuleConverter : AttributeConverter<EnableStatusRule, Int> {
    override fun convertToDatabaseColumn(enableStatusRule: EnableStatusRule): Int {
        return enableStatusRule.code
    }

    override fun convertToEntityAttribute(code: Int): EnableStatusRule? {
        return EnableStatusRule.ofLegacyCode(code)
    }
}