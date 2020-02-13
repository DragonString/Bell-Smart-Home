package net.softbell.bsh.domain.entity;

import java.io.Serializable;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.softbell.bsh.domain.ItemCategoryRule;
import net.softbell.bsh.domain.ItemModeRule;
import net.softbell.bsh.domain.ItemTypeRule;


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
public class NodeItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_id", unique=true, nullable=false)
	private Long itemId;

	@Column(nullable=false, length=50)
	private String alias;

	@Column(name="control_mode", nullable=false)
	private Byte controlMode;

	@Column(name="item_index", nullable=false)
	private Byte itemIndex;

	@Column(name="item_mode", nullable=false)
	private ItemModeRule itemMode;
	
	@Column(name="item_category", nullable=false)
	private ItemCategoryRule itemCategory;

	@Column(name="item_name", nullable=false, length=50)
	private String itemName;

	@Column(name="item_type", nullable=false)
	private ItemTypeRule itemType;

	@OneToMany(mappedBy="nodeItem")
	private List<NodeActionItem> nodeActionItems;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="node_id", nullable=false)
	private Node node;

	@OneToMany(mappedBy="nodeItem")
	private List<NodeItemHistory> nodeItemHistories;

	public NodeActionItem addNodeActionItem(NodeActionItem nodeActionItem)
	{
		getNodeActionItems().add(nodeActionItem);
		nodeActionItem.setNodeItem(this);

		return nodeActionItem;
	}

	public NodeActionItem removeNodeActionItem(NodeActionItem nodeActionItem)
	{
		getNodeActionItems().remove(nodeActionItem);
		nodeActionItem.setNodeItem(null);

		return nodeActionItem;
	}

	public NodeItemHistory addNodeItemHistory(NodeItemHistory nodeItemHistory)
	{
		getNodeItemHistories().add(nodeItemHistory);
		nodeItemHistory.setNodeItem(this);

		return nodeItemHistory;
	}

	public NodeItemHistory removeNodeItemHistory(NodeItemHistory nodeItemHistory)
	{
		getNodeItemHistories().remove(nodeItemHistory);
		nodeItemHistory.setNodeItem(null);

		return nodeItemHistory;
	}
}