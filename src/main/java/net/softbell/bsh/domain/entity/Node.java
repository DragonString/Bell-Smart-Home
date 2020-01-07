package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @Description : 노드 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node")
@NamedQuery(name="Node.findAll", query="SELECT n FROM Node n")
public class Node implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="node_id", unique=true, nullable=false)
	private int nodeId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="approval_date")
	private Date approvalDate;

	@Column(name="control_mode", nullable=false)
	private byte controlMode;

	@Column(name="enable_status", nullable=false)
	private byte enableStatus;

	@Column(name="node_name", nullable=false, length=50)
	private String nodeName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_date", nullable=false)
	private Date registerDate;

	@Column(nullable=false, length=64)
	private String token;

	@Column(nullable=false, length=50)
	private String uid;

	@OneToMany(mappedBy="node")
	private List<NodeConnectionLog> nodeConnectionLogs;

	@OneToMany(mappedBy="node")
	private List<NodeGroupItem> nodeGroupItems;

	@OneToMany(mappedBy="node")
	private List<NodeItem> nodeItems;

	public NodeConnectionLog addNodeConnectionLog(NodeConnectionLog nodeConnectionLog)
	{
		getNodeConnectionLogs().add(nodeConnectionLog);
		nodeConnectionLog.setNode(this);

		return nodeConnectionLog;
	}

	public NodeConnectionLog removeNodeConnectionLog(NodeConnectionLog nodeConnectionLog)
	{
		getNodeConnectionLogs().remove(nodeConnectionLog);
		nodeConnectionLog.setNode(null);

		return nodeConnectionLog;
	}

	public NodeGroupItem addNodeGroupItem(NodeGroupItem nodeGroupItem)
	{
		getNodeGroupItems().add(nodeGroupItem);
		nodeGroupItem.setNode(this);

		return nodeGroupItem;
	}

	public NodeGroupItem removeNodeGroupItem(NodeGroupItem nodeGroupItem)
	{
		getNodeGroupItems().remove(nodeGroupItem);
		nodeGroupItem.setNode(null);

		return nodeGroupItem;
	}

	public NodeItem addNodeItem(NodeItem nodeItem)
	{
		getNodeItems().add(nodeItem);
		nodeItem.setNode(this);

		return nodeItem;
	}

	public NodeItem removeNodeItem(NodeItem nodeItem)
	{
		getNodeItems().remove(nodeItem);
		nodeItem.setNode(null);

		return nodeItem;
	}
}