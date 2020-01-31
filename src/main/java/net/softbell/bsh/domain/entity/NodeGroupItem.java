package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_group_item",
	uniqueConstraints={
		@UniqueConstraint(columnNames={"node_group_id", "node_id"})
	})
@NamedQuery(name="NodeGroupItem.findAll", query="SELECT n FROM NodeGroupItem n")
public class NodeGroupItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="group_item_id", unique=true, nullable=false)
	private long groupItemId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="assign_date", nullable=false)
	private Date assignDate;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="node_group_id", nullable=false, insertable=false, updatable=false)
	private NodeGroup nodeGroup;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="node_id", nullable=false, insertable=false, updatable=false)
	private Node node;
}