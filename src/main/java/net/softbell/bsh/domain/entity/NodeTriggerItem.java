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
 * @Description : 노드 트리거 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_trigger_item",
	uniqueConstraints={
		@UniqueConstraint(columnNames={"item_id", "trigger_id"})
	})
@NamedQuery(name="NodeTriggerItem.findAll", query="SELECT n FROM NodeTriggerItem n")
public class NodeTriggerItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="trigger_item_id", unique=true, nullable=false)
	private long triggerItemId;

	@Column(nullable=false, length=50)
	private String alias;

	@ManyToOne
	@JoinColumn(name="item_id", nullable=false, insertable=false, updatable=false)
	private NodeItem nodeItem;

	@ManyToOne
	@JoinColumn(name="trigger_id", nullable=false, insertable=false, updatable=false)
	private NodeTrigger nodeTrigger;
}