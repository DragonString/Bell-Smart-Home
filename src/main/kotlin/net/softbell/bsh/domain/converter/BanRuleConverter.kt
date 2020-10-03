package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.BanRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author : Bell(bell@softbell.net)
 * @description : 차단 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class BanRuleConverter : AttributeConverter<BanRule, Int> {
    override fun convertToDatabaseColumn(banRule: BanRule): Int {
        return banRule.code
    }

    override fun convertToEntityAttribute(code: Int): BanRule? {
        return BanRule.ofLegacyCode(code)
    }
}