package net.softbell.bsh.domain.entity;

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
 * @Description : 노드 트리거 액션 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_trigger_action")
@NamedQuery(name="NodeTriggerAction.findAll", query="SELECT n FROM NodeTriggerAction n")
public class NodeTriggerAction
{
	@EmbeddedId
	private NodeTriggerActionPK id;

	@ManyToOne
	@JoinColumn(name="trigger_id", nullable=false, insertable=false, updatable=false)
	private NodeTrigger nodeTrigger;

	@ManyToOne
	@JoinColumn(name="action_id_occur")
	private NodeAction nodeActionOccur;

	@ManyToOne
	@JoinColumn(name="action_id_restore")
	private NodeAction nodeActionRestore;
}