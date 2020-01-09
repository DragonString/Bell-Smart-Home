package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.PinModeRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 핀 모드 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class PinModeRuleConverter implements AttributeConverter<PinModeRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(PinModeRule pinModeRule)
	{
		if (pinModeRule == null)
			return null;
		
		return pinModeRule.getCode();
	}

	@Override
	public PinModeRule convertToEntityAttribute(Integer code)
	{
		return PinModeRule.ofLegacyCode(code);
	}
}
