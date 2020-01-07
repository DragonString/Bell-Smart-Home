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
@Table(name="node_action_item")
@NamedQuery(name="NodeActionItem.findAll", query="SELECT n FROM NodeActionItem n")
public class NodeActionItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private NodeActionItemPK id;

	@Column(name="pin_status", nullable=false)
	private short pinStatus;

	@ManyToOne
	@JoinColumn(name="item_id", nullable=false, insertable=false, updatable=false)
	private NodeItem nodeItem;

	@ManyToOne
	@JoinColumn(name="action_id", nullable=false, insertable=false, updatable=false)
	private NodeAction nodeAction;
}