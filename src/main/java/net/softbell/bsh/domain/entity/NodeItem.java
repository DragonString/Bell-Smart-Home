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
 * @Description : 노드 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_item")
@NamedQuery(name="NodeItem.findAll", query="SELECT n FROM NodeItem n")
public class NodeItem
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_id", unique=true, nullable=false)
	private int itemId;

	@Column(nullable=false, length=50)
	private String alias;

	@Column(name="control_mode", nullable=false)
	private byte controlMode;

	@Column(name="pin_id", nullable=false)
	private byte pinId;

	@Column(name="pin_mode", nullable=false)
	private byte pinMode;

	@Column(name="pin_name", nullable=false, length=50)
	private String pinName;

	@Column(name="pin_type", nullable=false)
	private byte pinType;

	@OneToMany(mappedBy="nodeItem")
	private List<NodeActionItem> nodeActionItems;

	@ManyToOne
	@JoinColumn(name="node_id", nullable=false)
	private Node node;

	@OneToMany(mappedBy="nodeItem")
	private List<NodeItemHistory> nodeItemHistories;

	@OneToMany(mappedBy="nodeItem")
	private List<NodeTriggerItem> nodeTriggerItems;

	public NodeActionItem addNodeActionItem(NodeActionItem nodeActionItem) {
		getNodeActionItems().add(nodeActionItem);
		nodeActionItem.setNodeItem(this);

		return nodeActionItem;
	}

	public NodeActionItem removeNodeActionItem(NodeActionItem nodeActionItem) {
		getNodeActionItems().remove(nodeActionItem);
		nodeActionItem.setNodeItem(null);

		return nodeActionItem;
	}

	public NodeItemHistory addNodeItemHistory(NodeItemHistory nodeItemHistory) {
		getNodeItemHistories().add(nodeItemHistory);
		nodeItemHistory.setNodeItem(this);

		return nodeItemHistory;
	}

	public NodeItemHistory removeNodeItemHistory(NodeItemHistory nodeItemHistory) {
		getNodeItemHistories().remove(nodeItemHistory);
		nodeItemHistory.setNodeItem(null);

		return nodeItemHistory;
	}

	public NodeTriggerItem addNodeTriggerItem(NodeTriggerItem nodeTriggerItem) {
		getNodeTriggerItems().add(nodeTriggerItem);
		nodeTriggerItem.setNodeItem(this);

		return nodeTriggerItem;
	}

	public NodeTriggerItem removeNodeTriggerItem(NodeTriggerItem nodeTriggerItem) {
		getNodeTriggerItems().remove(nodeTriggerItem);
		nodeTriggerItem.setNodeItem(null);

		return nodeTriggerItem;
	}
}