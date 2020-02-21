package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.TriggerStatusRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 상태 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class TriggerStatusRuleConverter implements AttributeConverter<TriggerStatusRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(TriggerStatusRule triggerStatusRule)
	{
		if (triggerStatusRule == null)
			return null;
		
		return triggerStatusRule.getCode();
	}

	@Override
	public TriggerStatusRule convertToEntityAttribute(Integer code)
	{
		return TriggerStatusRule.ofLegacyCode(code);
	}
}
