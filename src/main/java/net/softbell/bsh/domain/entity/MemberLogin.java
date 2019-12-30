package net.softbell.bsh.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member_login")
@NamedQuery(name="MemberLogin.findAll", query="SELECT m FROM MemberLogin m")
public class MemberLogin implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MemberLoginPK id;

	@Column(name="login_ip", nullable=false, length=18)
	private String loginIp;

	@Column(name="login_statue", nullable=false)
	private short loginStatue;

	//bi-directional many-to-one association to MemberInfo
	@ManyToOne
	@JoinColumn(name="member_id", nullable=false, insertable=false, updatable=false)
	private MemberInfo memberInfo;

}