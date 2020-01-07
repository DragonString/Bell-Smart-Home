package net.softbell.bsh.dto.member;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
public class MemberDTO {
    private long memberId;
    private byte ban;
	private Date banDate;
	private Date changePasswdDate;
	private String emailAddress;
	private String emailHost;
	private String emailId;
	private Date lastLogin;
	private byte loginFailcount;
	private String nickname;
	private String passwd;
	private byte permission;
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
                .passwd(passwd)
        		.emailHost(emailHost)
        		.emailId(emailId)
                .username(username)
        		.nickname(nickname)
                .registerDate(new Date())
                .build();
    }
}