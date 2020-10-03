package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.ItemTypeRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 아이템 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class ItemTypeRuleConverter : AttributeConverter<ItemTypeRule, Int> {
    override fun convertToDatabaseColumn(pinTypeRule: ItemTypeRule): Int {
        return pinTypeRule.code
    }

    override fun convertToEntityAttribute(code: Int): ItemTypeRule? {
        return ItemTypeRule.ofLegacyCode(code)
    }
}