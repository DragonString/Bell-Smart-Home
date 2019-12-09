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
@Table(name="node_type")
@NamedQuery(name="NodeType.findAll", query="SELECT n FROM NodeType n")
public class NodeType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="type_id", unique=true, nullable=false)
	private int typeId;

	@Column(name="control_state", nullable=false)
	private short controlState;

	@Column(name="data_history_state", nullable=false)
	private short dataHistoryState;

	@Column(name="type_name", nullable=false, length=20)
	private String typeName;

	//bi-directional many-to-one association to NodeItemProperty
	@OneToMany(mappedBy="nodeType")
	private List<NodeProperty> nodeItemProperties;

	public NodeProperty addNodeItemProperty(NodeProperty nodeItemProperty) {
		getNodeItemProperties().add(nodeItemProperty);
		nodeItemProperty.setNodeType(this);

		return nodeItemProperty;
	}

	public NodeProperty removeNodeItemProperty(NodeProperty nodeItemProperty) {
		getNodeItemProperties().remove(nodeItemProperty);
		nodeItemProperty.setNodeType(null);

		return nodeItemProperty;
	}

}