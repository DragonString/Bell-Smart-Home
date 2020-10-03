package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.ItemCategoryRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 아이템 카테고리 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class ItemCategoryRuleConverter : AttributeConverter<ItemCategoryRule, Int> {
    override fun convertToDatabaseColumn(pinModeRule: ItemCategoryRule): Int {
        return pinModeRule.code
    }

    override fun convertToEntityAttribute(code: Int): ItemCategoryRule? {
        return ItemCategoryRule.ofLegacyCode(code)
    }
}