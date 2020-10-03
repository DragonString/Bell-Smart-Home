package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.ItemModeRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 아이템 모드 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class ItemModeRuleConverter : AttributeConverter<ItemModeRule, Int> {
    override fun convertToDatabaseColumn(pinModeRule: ItemModeRule): Int {
        return pinModeRule.code
    }

    override fun convertToEntityAttribute(code: Int): ItemModeRule? {
        return ItemModeRule.ofLegacyCode(code)
    }
}