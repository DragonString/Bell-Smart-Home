package net.softbell.bsh.domain.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @Description : 노드 그룹 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_group")
@NamedQuery(name="NodeGroup.findAll", query="SELECT n FROM NodeGroup n")
public class NodeGroup
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="node_group_id", unique=true, nullable=false)
	private int nodeGroupId;

	@Column(name="enable_status", nullable=false)
	private byte enableStatus;

	@Column(nullable=false, length=50)
	private String name;

	@Column(nullable=false)
	private byte type;

	@OneToMany(mappedBy="nodeGroup")
	private List<GroupPermission> groupPermissions;

	@OneToMany(mappedBy="nodeGroup")
	private List<NodeGroupItem> nodeGroupItems;

	public GroupPermission addGroupPermission(GroupPermission groupPermission) {
		getGroupPermissions().add(groupPermission);
		groupPermission.setNodeGroup(this);

		return groupPermission;
	}

	public GroupPermission removeGroupPermission(GroupPermission groupPermission) {
		getGroupPermissions().remove(groupPermission);
		groupPermission.setNodeGroup(null);

		return groupPermission;
	}

	public NodeGroupItem addNodeGroupItem(NodeGroupItem nodeGroupItem) {
		getNodeGroupItems().add(nodeGroupItem);
		nodeGroupItem.setNodeGroup(this);

		return nodeGroupItem;
	}

	public NodeGroupItem removeNodeGroupItem(NodeGroupItem nodeGroupItem) {
		getNodeGroupItems().remove(nodeGroupItem);
		nodeGroupItem.setNodeGroup(null);

		return nodeGroupItem;
	}
}