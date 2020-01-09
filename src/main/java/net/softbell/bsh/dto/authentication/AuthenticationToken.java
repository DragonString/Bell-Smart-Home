package net.softbell.bsh.dto.authentication;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 토큰 DTO
 */
@AllArgsConstructor
@Setter
@Getter
public class AuthenticationToken
{
	private String username;
    private Collection authorities;
    private String token;
}
