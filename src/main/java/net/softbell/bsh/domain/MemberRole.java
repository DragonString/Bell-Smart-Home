package net.softbell.bsh.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 권한 자료형
 */
@AllArgsConstructor
@Getter
public enum MemberRole
{
    SUPERADMIN("ROLE_SUPERADMIN"),
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER"),
    WAIT("ROLE_WAIT"),
    BAN("ROLE_BAN");

    private String value;
}