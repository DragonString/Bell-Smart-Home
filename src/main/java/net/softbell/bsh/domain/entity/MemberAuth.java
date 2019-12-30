package net.softbell.bsh.domain.entity;

import java.io.Serializable;
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


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member_auth")
@NamedQuery(name="MemberAuth.findAll", query="SELECT m FROM MemberAuth m")
public class MemberAuth implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MemberAuthPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="set_time", nullable=false)
	private Date setTime;

	//bi-directional many-to-one association to MemberAuthType
	@ManyToOne
	@JoinColumn(name="auth_id", nullable=false, insertable=false, updatable=false)
	private MemberAuthType memberAuthType;

	//bi-directional many-to-one association to MemberInfo
	@ManyToOne
	@JoinColumn(name="member_id", nullable=false, insertable=false, updatable=false)
	private MemberInfo memberInfo;

	//bi-directional many-to-one association to NodeInfo
	@ManyToOne
	@JoinColumn(name="node_id", nullable=false, insertable=false, updatable=false)
	private NodeInfo nodeInfo;
}