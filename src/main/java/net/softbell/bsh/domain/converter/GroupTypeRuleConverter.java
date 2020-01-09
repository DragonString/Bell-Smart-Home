package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.GroupTypeRule;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class GroupTypeRuleConverter implements AttributeConverter<GroupTypeRule, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(GroupTypeRule groupTypeRule)
	{
		if (groupTypeRule == null)
			return null;
		
		return groupTypeRule.getCode();
	}

	@Override
	public GroupTypeRule convertToEntityAttribute(Integer code)
	{
		return GroupTypeRule.ofLegacyCode(code);
	}
}
