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
 * @Description : 노드 예약 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_reserv")
@NamedQuery(name="NodeReserv.findAll", query="SELECT n FROM NodeReserv n")
public class NodeReserv
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reserv_id", unique=true, nullable=false)
	private int reservId;

	@Column(nullable=false, length=50)
	private String description;

	@Column(name="enable_status", nullable=false)
	private byte enableStatus;

	@Column(nullable=false, length=100)
	private String expression;

	@OneToMany(mappedBy="nodeReserv")
	private List<NodeReservAction> nodeReservActions;

	@ManyToOne
	@JoinColumn(name="member_id", nullable=false)
	private Member member;

	public NodeReservAction addNodeReservAction(NodeReservAction nodeReservAction) {
		getNodeReservActions().add(nodeReservAction);
		nodeReservAction.setNodeReserv(this);

		return nodeReservAction;
	}

	public NodeReservAction removeNodeReservAction(NodeReservAction nodeReservAction) {
		getNodeReservActions().remove(nodeReservAction);
		nodeReservAction.setNodeReserv(null);

		return nodeReservAction;
	}
}