package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.TriggerLastStatusRule;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 트리거 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_trigger")
@NamedQuery(name="NodeTrigger.findAll", query="SELECT n FROM NodeTrigger n")
public class NodeTrigger implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="trigger_id", unique=true, nullable=false)
	private Long triggerId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="change_date")
	private Date changeDate;

	@Column(nullable=false, length=100)
	private String description;

	@Column(name="enable_status", nullable=false)
	private EnableStatusRule enableStatus;

	@Column(nullable=false, length=100)
	private String expression;

	@Column(name="last_status", nullable=false)
	private TriggerLastStatusRule lastStatus;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_id", nullable=false)
	private Member member;

	@OneToMany(mappedBy="nodeTrigger")
	private List<NodeTriggerOccurAction> nodeTriggerOccurActions;

	@OneToMany(mappedBy="nodeTrigger")
	private List<NodeTriggerRestoreAction> nodeTriggerRestoreActions;

	public NodeTriggerOccurAction addNodeTriggerOccurAction(NodeTriggerOccurAction nodeTriggerOccurAction)
	{
		getNodeTriggerOccurActions().add(nodeTriggerOccurAction);
		nodeTriggerOccurAction.setNodeTrigger(this);

		return nodeTriggerOccurAction;
	}

	public NodeTriggerOccurAction removeNodeTriggerOccurAction(NodeTriggerOccurAction nodeTriggerOccurAction)
	{
		getNodeTriggerOccurActions().remove(nodeTriggerOccurAction);
		nodeTriggerOccurAction.setNodeTrigger(null);

		return nodeTriggerOccurAction;
	}

	public NodeTriggerRestoreAction addNodeTriggerRestoreAction(NodeTriggerRestoreAction nodeTriggerRestoreAction)
	{
		getNodeTriggerRestoreActions().add(nodeTriggerRestoreAction);
		nodeTriggerRestoreAction.setNodeTrigger(this);

		return nodeTriggerRestoreAction;
	}

	public NodeTriggerRestoreAction removeNodeTriggerOccurAction(NodeTriggerRestoreAction nodeTriggerRestoreAction)
	{
		getNodeTriggerRestoreActions().remove(nodeTriggerRestoreAction);
		nodeTriggerRestoreAction.setNodeTrigger(null);

		return nodeTriggerRestoreAction;
	}
}