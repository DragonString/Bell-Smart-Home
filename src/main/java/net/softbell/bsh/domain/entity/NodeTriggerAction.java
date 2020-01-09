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
public class NodeTriggerAction implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="trigger_action_id", unique=true, nullable=false)
	private long triggerActionId;

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