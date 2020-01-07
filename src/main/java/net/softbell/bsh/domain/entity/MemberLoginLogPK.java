package net.softbell.bsh.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 로그인 로그 주 키
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class MemberLoginLogPK implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Column(name="member_id", insertable=false, updatable=false, unique=true, nullable=false)
	private long memberId;

	@Column(name="log_id", unique=true, nullable=false)
	private String logId;
}