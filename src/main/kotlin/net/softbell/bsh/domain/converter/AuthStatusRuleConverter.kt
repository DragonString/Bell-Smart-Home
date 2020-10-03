package net.softbell.bsh.domain.converter

import net.softbell.bsh.domain.AuthStatusRule
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author : Bell(bell@softbell.net)
 * @description : 인증 상태 자료형 DB 컨버터
 */
@Converter(autoApply = true)
class AuthStatusRuleConverter : AttributeConverter<AuthStatusRule, Int> {
    override fun convertToDatabaseColumn(authStatusRule: AuthStatusRule): Int {
        return authStatusRule.code
    }

    override fun convertToEntityAttribute(code: Int): AuthStatusRule? {
        return AuthStatusRule.ofLegacyCode(code)
    }
}