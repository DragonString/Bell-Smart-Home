package net.softbell.bsh.dto.authentication;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AuthenticationToken
{
	private String username;
    private Collection authorities;
    private String token;
}
