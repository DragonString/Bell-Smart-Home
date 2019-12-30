package net.softbell.bsh.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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