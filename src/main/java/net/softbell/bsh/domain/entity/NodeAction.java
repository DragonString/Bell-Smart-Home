package net.softbell.bsh.domain.entity;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 액션 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_action")
@NamedQuery(name="NodeAction.findAll", query="SELECT n FROM NodeAction n")
public class NodeAction
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="action_id", unique=true, nullable=false)
	private int actionId;

	@Column(nullable=false, length=50)
	private String description;

	@Column(name="enable_status", nullable=false)
	private byte enableStatus;

	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;

	@OneToMany(mappedBy="nodeAction")
	private List<NodeActionItem> nodeActionItems;

	@OneToMany(mappedBy="nodeAction")
	private List<NodeReservAction> nodeReservActions;

	@OneToMany(mappedBy="nodeActionOccur")
	private List<NodeTriggerAction> nodeTriggerActionsOccur;

	@OneToMany(mappedBy="nodeActionRestore")
	private List<NodeTriggerAction> nodeTriggerActionsRestore;

	public NodeActionItem addNodeActionItem(NodeActionItem nodeActionItem) {
		getNodeActionItems().add(nodeActionItem);
		nodeActionItem.setNodeAction(this);

		return nodeActionItem;
	}

	public NodeActionItem removeNodeActionItem(NodeActionItem nodeActionItem) {
		getNodeActionItems().remove(nodeActionItem);
		nodeActionItem.setNodeAction(null);

		return nodeActionItem;
	}

	public NodeReservAction addNodeReservAction(NodeReservAction nodeReservAction) {
		getNodeReservActions().add(nodeReservAction);
		nodeReservAction.setNodeAction(this);

		return nodeReservAction;
	}

	public NodeReservAction removeNodeReservAction(NodeReservAction nodeReservAction) {
		getNodeReservActions().remove(nodeReservAction);
		nodeReservAction.setNodeAction(null);

		return nodeReservAction;
	}

	public NodeTriggerAction addNodeTriggerActions1(NodeTriggerAction nodeTriggerActionsOccur) {
		getNodeTriggerActionsOccur().add(nodeTriggerActionsOccur);
		nodeTriggerActionsOccur.setNodeActionOccur(this);

		return nodeTriggerActionsOccur;
	}

	public NodeTriggerAction removeNodeTriggerActions1(NodeTriggerAction nodeTriggerActionsOccur) {
		getNodeTriggerActionsOccur().remove(nodeTriggerActionsOccur);
		nodeTriggerActionsOccur.setNodeActionOccur(null);

		return nodeTriggerActionsOccur;
	}

	public NodeTriggerAction addNodeTriggerActions2(NodeTriggerAction nodeTriggerActionsRestore) {
		getNodeTriggerActionsRestore().add(nodeTriggerActionsRestore);
		nodeTriggerActionsRestore.setNodeActionRestore(this);

		return nodeTriggerActionsRestore;
	}

	public NodeTriggerAction removeNodeTriggerActions2(NodeTriggerAction nodeTriggerActionsRestore) {
		getNodeTriggerActionsRestore().remove(nodeTriggerActionsRestore);
		nodeTriggerActionsRestore.setNodeActionRestore(null);

		return nodeTriggerActionsRestore;
	}
}