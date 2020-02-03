package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.ItemTypeRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 아이템 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class ItemTypeRuleConverter implements AttributeConverter<ItemTypeRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(ItemTypeRule pinTypeRule)
	{
		if (pinTypeRule == null)
			return null;
		
		return pinTypeRule.getCode();
	}

	@Override
	public ItemTypeRule convertToEntityAttribute(Integer code)
	{
		return ItemTypeRule.ofLegacyCode(code);
	}
}
