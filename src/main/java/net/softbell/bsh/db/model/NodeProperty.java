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
@Table(name="node_property")
@NamedQuery(name="NodeProperty.findAll", query="SELECT n FROM NodeProperty n")
public class NodeProperty implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodePropertyPK id;

	@Column(name="item_status", nullable=false)
	private short itemStatus;

	@Column(name="mode_state", nullable=false)
	private short modeState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time", nullable=false)
	private Date updateTime;

	//bi-directional many-to-one association to NodeData
	@OneToMany(mappedBy="nodeItemProperty")
	private List<NodeData> nodeData;

	//bi-directional many-to-one association to NodeInfo
	@ManyToOne
	@JoinColumn(name="node_id", nullable=false, insertable=false, updatable=false)
	private NodeInfo nodeInfo;

	//bi-directional many-to-one association to NodeType
	@ManyToOne
	@JoinColumn(name="type_id", nullable=false, insertable=false, updatable=false)
	private NodeType nodeType;

	//bi-directional many-to-one association to NodeReserv
	@OneToMany(mappedBy="nodeItemProperty")
	private List<NodeReserv> nodeReservs;

	public NodeData addNodeData(NodeData nodeData) {
		getNodeData().add(nodeData);
		nodeData.setNodeItemProperty(this);

		return nodeData;
	}

	public NodeData removeNodeData(NodeData nodeData) {
		getNodeData().remove(nodeData);
		nodeData.setNodeItemProperty(null);

		return nodeData;
	}

	public NodeReserv addNodeReserv(NodeReserv nodeReserv) {
		getNodeReservs().add(nodeReserv);
		nodeReserv.setNodeItemProperty(this);

		return nodeReserv;
	}

	public NodeReserv removeNodeReserv(NodeReserv nodeReserv) {
		getNodeReservs().remove(nodeReserv);
		nodeReserv.setNodeItemProperty(null);

		return nodeReserv;
	}

}