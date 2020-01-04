package net.softbell.bsh.dto.member;

import java.util.Calendar;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.softbell.bsh.domain.entity.MemberInfo;

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
public class MemberInfoDTO {
    private int id;
    private String userId;
    private String password;
    private Date regDate;
    private short isBan;
    private byte isAdmin;
    private String username;
    //private LocalDateTime modifiedDate;
    private int year, month, day;

    public MemberInfo toEntity(){
    	// Field
    	Calendar calendar = Calendar.getInstance();
    	
    	// Init
    	calendar.clear(); // Sets hours/minutes/seconds/milliseconds to zero
    	calendar.set(year, month - 1, day);
    	
    	// Default
    	/*if (birthday == null)
    		birthday = calendar.getTime();
        /*if (isBan == null)
        	isBan = "N";
        if (isAdmin == null)
        	isAdmin = "N";*/
        
        // Return
        return MemberInfo.builder()
                .userId(userId)
                .password(password)
                .registerTime(new Date())
                .isBan(isBan)
                .isAdmin(isAdmin)
                .username(username)
                .build();
    }

    public MemberInfoDTO(String userId, String password, String username) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }
}