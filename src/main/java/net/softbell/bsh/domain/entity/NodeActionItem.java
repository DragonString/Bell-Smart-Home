package net.softbell.bsh.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_action_item",
	uniqueConstraints={
		@UniqueConstraint(columnNames={"item_id", "action_id"})
	})
@NamedQuery(name="NodeActionItem.findAll", query="SELECT n FROM NodeActionItem n")
public class NodeActionItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="action_item_id", unique=true, nullable=false)
	private long actionItemId;

	@Column(name="pin_status", nullable=false)
	private short pinStatus;

	@ManyToOne
	@JoinColumn(name="item_id", nullable=false)
	private NodeItem nodeItem;

	@ManyToOne
	@JoinColumn(name="action_id", nullable=false)
	private NodeAction nodeAction;
}