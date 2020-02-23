package net.softbell.bsh.dto.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.softbell.bsh.domain.BanRule;
import net.softbell.bsh.domain.MemberRole;
import net.softbell.bsh.domain.entity.Member;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원정보 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto
{
    private Long memberId;
    private Integer ban;
	private Date banDate;
	private Date changePasswdDate;
	private String email;
	private Date lastLogin;
	private Integer loginFailCount;
	private String nickname;
	private String password;
	private Integer permission;
	private Date registerDate;
	private String userId;
	private String username;

    public Member toEntity()
    {
    	// Generate
        return Member.builder()
                .userId(userId)
        		.email(email)
                .password(password)
                .name(username)
        		.nickname(nickname)
                .registerDate(new Date())
                .ban(BanRule.ofLegacyCode(ban))
                .loginFailCount(loginFailCount)
                .permission(MemberRole.ofLegacyCode(permission))
                .build();
    }
}