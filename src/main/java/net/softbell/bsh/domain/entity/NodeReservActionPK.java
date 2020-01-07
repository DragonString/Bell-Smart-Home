package net.softbell.bsh.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 예약 액션 주 키
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class NodeReservActionPK implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Column(name="reserv_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int reservId;

	@Column(name="reserv_action_id", unique=true, nullable=false)
	private byte reservActionId;
}