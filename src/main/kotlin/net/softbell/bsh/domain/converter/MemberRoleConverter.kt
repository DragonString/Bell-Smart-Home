package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.MemberRole
import javax.persistence.AttributeConverter
import javax.persistence.Converter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 권한 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class MemberRoleConverter : AttributeConverter<MemberRole?, Int?> {
    override fun convertToDatabaseColumn(memberRole: MemberRole?): Int? {
        return memberRole?.getCode()
    }

    override fun convertToEntityAttribute(code: Int?): MemberRole? {
        return MemberRole.Companion.ofLegacyCode(code)
    }
}