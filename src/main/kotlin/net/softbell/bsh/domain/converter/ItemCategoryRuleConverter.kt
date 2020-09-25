package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.ItemCategoryRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 카테고리 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class ItemCategoryRuleConverter implements AttributeConverter<ItemCategoryRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(ItemCategoryRule pinModeRule)
	{
		if (pinModeRule == null)
			return null;
		
		return pinModeRule.getCode();
	}

	@Override
	public ItemCategoryRule convertToEntityAttribute(Integer code)
	{
		return ItemCategoryRule.ofLegacyCode(code);
	}
}
