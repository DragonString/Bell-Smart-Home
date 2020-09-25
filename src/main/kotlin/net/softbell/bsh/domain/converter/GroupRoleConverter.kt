package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.GroupRole;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 권한 타입 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class GroupRoleConverter implements AttributeConverter<GroupRole, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(GroupRole groupRole)
	{
		if (groupRole == null)
			return null;
		
		return groupRole.getCode();
	}

	@Override
	public GroupRole convertToEntityAttribute(Integer code)
	{
		return GroupRole.ofLegacyCode(code);
	}
}
