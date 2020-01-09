package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.PinTypeRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 핀 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class PinTypeRuleConverter implements AttributeConverter<PinTypeRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(PinTypeRule pinTypeRule)
	{
		if (pinTypeRule == null)
			return null;
		
		return pinTypeRule.getCode();
	}

	@Override
	public PinTypeRule convertToEntityAttribute(Integer code)
	{
		return PinTypeRule.ofLegacyCode(code);
	}
}
