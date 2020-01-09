package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.TriggerLastStatusRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 마지막 상태 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class EnableStatusRuleConverter implements AttributeConverter<TriggerLastStatusRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(TriggerLastStatusRule triggerLastStatusRule)
	{
		if (triggerLastStatusRule == null)
			return null;
		
		return triggerLastStatusRule.getCode();
	}

	@Override
	public TriggerLastStatusRule convertToEntityAttribute(Integer code)
	{
		return TriggerLastStatusRule.ofLegacyCode(code);
	}
}
