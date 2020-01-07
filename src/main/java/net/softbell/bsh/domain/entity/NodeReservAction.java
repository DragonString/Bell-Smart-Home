package net.softbell.bsh.domain.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
	
	@EmbeddedId
	private NodeReservActionPK id;

	@ManyToOne
	@JoinColumn(name="reserv_id", nullable=false, insertable=false, updatable=false)
	private NodeReserv nodeReserv;

	@ManyToOne
	@JoinColumn(name="action_id", nullable=false)
	private NodeAction nodeAction;
}