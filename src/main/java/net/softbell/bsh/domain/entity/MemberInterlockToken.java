package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 연동 토큰 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member_interlock_token",
	uniqueConstraints={
		@UniqueConstraint(columnNames={"token"})
	})
@NamedQuery(name="MemberInterlockToken.findAll", query="SELECT m FROM MemberInterlockToken m")
public class MemberInterlockToken implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_interlock_id", unique=true, nullable=false)
	private Long memberInterlockId;
	
	@Column(name="enable_status", nullable=false)
	private EnableStatusRule enableStatus;
	
	@Column(name="token", nullable=false)
	private String token;
	
	@Column(name="name", nullable=false)
	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_date", nullable=false)
	private Date registerDate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	private Member member;
}