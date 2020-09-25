package net.softbell.bsh.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import net.softbell.bsh.domain.MemberRole;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 권한 자료형 DB 컨버터
 */
@Converter(autoApply = true)
public class MemberRoleConverter implements AttributeConverter<MemberRole, Integer>
{
	@Override
	public Integer convertToDatabaseColumn(MemberRole memberRole)
	{
		if (memberRole == null)
			return null;
		
		return memberRole.getCode();
	}

	@Override
	public MemberRole convertToEntityAttribute(Integer code)
	{
		return MemberRole.ofLegacyCode(code);
	}
}
