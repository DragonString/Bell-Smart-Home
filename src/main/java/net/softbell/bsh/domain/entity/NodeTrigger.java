package net.softbell.bsh.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class NodeTrigger
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="trigger_id", unique=true, nullable=false)
	private int triggerId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="change_date")
	private Date changeDate;

	@Column(nullable=false, length=100)
	private String description;

	@Column(name="enable_status", nullable=false)
	private byte enableStatus;

	@Column(nullable=false, length=100)
	private String expression;

	@Column(nullable=false)
	private byte status;

	@ManyToOne
	@JoinColumn(name="member_id", nullable=false)
	private Member member;

	@OneToMany(mappedBy="nodeTrigger")
	private List<NodeTriggerAction> nodeTriggerActions;

	@OneToMany(mappedBy="nodeTrigger")
	private List<NodeTriggerItem> nodeTriggerItems;

	public NodeTriggerAction addNodeTriggerAction(NodeTriggerAction nodeTriggerAction) {
		getNodeTriggerActions().add(nodeTriggerAction);
		nodeTriggerAction.setNodeTrigger(this);

		return nodeTriggerAction;
	}

	public NodeTriggerAction removeNodeTriggerAction(NodeTriggerAction nodeTriggerAction) {
		getNodeTriggerActions().remove(nodeTriggerAction);
		nodeTriggerAction.setNodeTrigger(null);

		return nodeTriggerAction;
	}

	public NodeTriggerItem addNodeTriggerItem(NodeTriggerItem nodeTriggerItem) {
		getNodeTriggerItems().add(nodeTriggerItem);
		nodeTriggerItem.setNodeTrigger(this);

		return nodeTriggerItem;
	}

	public NodeTriggerItem removeNodeTriggerItem(NodeTriggerItem nodeTriggerItem) {
		getNodeTriggerItems().remove(nodeTriggerItem);
		nodeTriggerItem.setNodeTrigger(null);

		return nodeTriggerItem;
	}
}