package net.softbell.bsh.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name="node_info")
@NamedQuery(name="NodeInfo.findAll", query="SELECT n FROM NodeInfo n")
public class NodeInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="node_id", unique=true, nullable=false)
	private int nodeId;

	@Column(name="chip_id", nullable=false)
	private int chipId;

	@Column(name="mode_state", nullable=false)
	private byte modeState;

	@Column(name="node_name", length=20)
	private String nodeName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_time", nullable=false)
	private Date registerTime;

	//bi-directional many-to-one association to MemberAuth
	@OneToMany(mappedBy="nodeInfo")
	private List<MemberAuth> memberAuths;

	//bi-directional many-to-one association to NodeConnection
	@OneToMany(mappedBy="nodeInfo")
	private List<NodeConnection> nodeConnections;

	//bi-directional many-to-one association to NodeItemProperty
	@OneToMany(mappedBy="nodeInfo")
	private List<NodeProperty> nodeItemProperties;

	public MemberAuth addMemberAuth(MemberAuth memberAuth) {
		getMemberAuths().add(memberAuth);
		memberAuth.setNodeInfo(this);

		return memberAuth;
	}

	public MemberAuth removeMemberAuth(MemberAuth memberAuth) {
		getMemberAuths().remove(memberAuth);
		memberAuth.setNodeInfo(null);

		return memberAuth;
	}

	public NodeConnection addNodeConnection(NodeConnection nodeConnection) {
		getNodeConnections().add(nodeConnection);
		nodeConnection.setNodeInfo(this);

		return nodeConnection;
	}

	public NodeConnection removeNodeConnection(NodeConnection nodeConnection) {
		getNodeConnections().remove(nodeConnection);
		nodeConnection.setNodeInfo(null);

		return nodeConnection;
	}

	public NodeProperty addNodeItemProperty(NodeProperty nodeItemProperty) {
		getNodeItemProperties().add(nodeItemProperty);
		nodeItemProperty.setNodeInfo(this);

		return nodeItemProperty;
	}

	public NodeProperty removeNodeItemProperty(NodeProperty nodeItemProperty) {
		getNodeItemProperties().remove(nodeItemProperty);
		nodeItemProperty.setNodeInfo(null);

		return nodeItemProperty;
	}

}