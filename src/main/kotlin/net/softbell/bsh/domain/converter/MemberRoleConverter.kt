package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.MemberRole
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 권한 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class MemberRoleConverter : AttributeConverter<MemberRole, Int> {
    override fun convertToDatabaseColumn(memberRole: MemberRole): Int {
        return memberRole.code
    }

    override fun convertToEntityAttribute(code: Int): MemberRole? {
        return MemberRole.ofLegacyCode(code)
    }
}