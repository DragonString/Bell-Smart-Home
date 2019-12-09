package net.softbell.bsh.db.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.softbell.bsh.db.model.NodeInfo.NodeInfoBuilder;


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