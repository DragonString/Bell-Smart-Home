package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.ItemModeRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 모드 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class ItemModeRuleConverter implements AttributeConverter<ItemModeRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(ItemModeRule pinModeRule)
	{
		if (pinModeRule == null)
			return null;
		
		return pinModeRule.getCode();
	}

	@Override
	public ItemModeRule convertToEntityAttribute(Integer code)
	{
		return ItemModeRule.ofLegacyCode(code);
	}
}
