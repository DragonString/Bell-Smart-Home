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
@Table(name="node_connection")
@NamedQuery(name="NodeConnection.findAll", query="SELECT n FROM NodeConnection n")
public class NodeConnection implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodeConnectionPK id;

	@Column(name="con_ip", nullable=false, length=18)
	private String conIp;

	@Column(name="con_status", nullable=false)
	private byte conStatus;

	//bi-directional many-to-one association to NodeInfo
	@ManyToOne
	@JoinColumn(name="node_id", nullable=false, insertable=false, updatable=false)
	private NodeInfo nodeInfo;

}