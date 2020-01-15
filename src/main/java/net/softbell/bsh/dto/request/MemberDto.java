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
public class MemberDto {
    private long memberId;
    private int ban;
	private Date banDate;
	private Date changePasswdDate;
	private String emailAddress;
	private String emailHost;
	private String emailId;
	private Date lastLogin;
	private int loginFailcount;
	private String nickname;
	private String passwd;
	private int permission;
	private Date registerDate;
	private String userId;
	private String username;

    public Member toEntity(){
    	// Default
    	if (emailAddress == null || emailAddress.isEmpty())
    		emailAddress = emailHost + "@" + emailId;
    	
    	// Generate
        return Member.builder()
                .userId(userId)
        		.emailAddress(emailAddress)
                .password(passwd)
        		.emailHost(emailHost)
        		.emailId(emailId)
                .name(username)
        		.nickname(nickname)
                .registerDate(new Date())
                .ban(BanRule.ofLegacyCode(ban))
                .permission(MemberRole.ofLegacyCode(permission))
                .build();
    }
}