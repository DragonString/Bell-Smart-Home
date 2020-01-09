package net.softbell.bsh.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 인증 데이터 DTO
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthenticationRequest
{
	private String username;
    private String password;
}
