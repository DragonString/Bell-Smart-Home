package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.ItemCategoryRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 카테고리 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class ItemCategoryRuleConverter : AttributeConverter<ItemCategoryRule?, Int?> {
    override fun convertToDatabaseColumn(pinModeRule: ItemCategoryRule?): Int? {
        return pinModeRule?.getCode()
    }

    override fun convertToEntityAttribute(code: Int?): ItemCategoryRule? {
        return ItemCategoryRule.Companion.ofLegacyCode(code)
    }
}