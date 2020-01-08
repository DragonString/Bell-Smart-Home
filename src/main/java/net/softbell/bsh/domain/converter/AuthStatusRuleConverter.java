package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.AuthStatusRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 상태 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class AuthStatusRuleConverter implements AttributeConverter<AuthStatusRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(AuthStatusRule authStatusRule)
	{
		if (authStatusRule == null)
			return null;
		
		return authStatusRule.getCode();
	}

	@Override
	public AuthStatusRule convertToEntityAttribute(Integer code)
	{
		return AuthStatusRule.ofLegacyCode(code);
	}
}
