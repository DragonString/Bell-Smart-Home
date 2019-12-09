package net.softbell.bsh.db.model;

import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.softbell.bsh.db.model.NodeInfo.NodeInfoBuilder;

import java.util.Date;
import java.util.List;


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