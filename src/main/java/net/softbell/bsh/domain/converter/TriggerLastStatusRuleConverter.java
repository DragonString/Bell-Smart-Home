package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.EnableStatusRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 트리거 마지막 상태 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class TriggerLastStatusRuleConverter implements AttributeConverter<EnableStatusRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(EnableStatusRule enableStatusRule)
	{
		if (enableStatusRule == null)
			return null;
		
		return enableStatusRule.getCode();
	}

	@Override
	public EnableStatusRule convertToEntityAttribute(Integer code)
	{
		return EnableStatusRule.ofLegacyCode(code);
	}
}
