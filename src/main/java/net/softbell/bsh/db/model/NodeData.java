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
@Table(name="node_data")
@NamedQuery(name="NodeData.findAll", query="SELECT n FROM NodeData n")
public class NodeData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NodeDataPK id;

	@Column(name="item_data", nullable=false)
	private int itemData;

	//bi-directional many-to-one association to NodeItemProperty
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="item_id", referencedColumnName="item_id", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="node_id", referencedColumnName="node_id", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="type_id", referencedColumnName="type_id", nullable=false, insertable=false, updatable=false)
		})
	private NodeProperty nodeItemProperty;

}