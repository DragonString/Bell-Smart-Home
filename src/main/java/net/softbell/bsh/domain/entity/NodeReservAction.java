package net.softbell.bsh.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
 * @Description : 노드 예약 액션 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_reserv_action")
@NamedQuery(name="NodeReservAction.findAll", query="SELECT n FROM NodeReservAction n")
public class NodeReservAction implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reserv_action_id", unique=true, nullable=false)
	private long reservActionId;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="reserv_id", nullable=false)
	private NodeReserv nodeReserv;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="action_id", nullable=false)
	private NodeAction nodeAction;
}