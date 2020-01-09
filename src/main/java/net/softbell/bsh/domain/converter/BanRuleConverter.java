package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.BanRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 차단 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class BanRuleConverter implements AttributeConverter<BanRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(BanRule banRule)
	{
		if (banRule == null)
			return null;
		
		return banRule.getCode();
	}

	@Override
	public BanRule convertToEntityAttribute(Integer code)
	{
		return BanRule.ofLegacyCode(code);
	}
}
