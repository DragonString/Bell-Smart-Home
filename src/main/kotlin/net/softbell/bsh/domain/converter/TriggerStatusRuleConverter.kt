package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.TriggerStatusRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author : Bell(bell@softbell.net)
 * @description : 인증 상태 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class TriggerStatusRuleConverter : AttributeConverter<TriggerStatusRule, Int> {
    override fun convertToDatabaseColumn(triggerStatusRule: TriggerStatusRule): Int {
        return triggerStatusRule.code
    }

    override fun convertToEntityAttribute(code: Int): TriggerStatusRule? {
        return TriggerStatusRule.ofLegacyCode(code)
    }
}