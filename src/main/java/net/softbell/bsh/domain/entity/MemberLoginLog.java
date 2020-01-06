package net.softbell.bsh.domain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 로그인 로그 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member_login_log")
@NamedQuery(name="MemberLoginLog.findAll", query="SELECT m FROM MemberLoginLog m")
public class MemberLoginLog
{
	@EmbeddedId
	private MemberLoginLogPK id;

	@Column(nullable=false, length=15)
	private String ipv4;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="request_date", nullable=false)
	private Date requestDate;

	@Column(nullable=false)
	private byte status;

	@ManyToOne
	@JoinColumn(name="member_id", nullable=false, insertable=false, updatable=false)
	private Member member;
}