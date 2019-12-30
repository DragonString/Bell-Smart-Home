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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class NodeReservPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="reserv_id", unique=true, nullable=false)
	private int reservId;

	@Column(name="node_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int nodeId;

	@Column(name="type_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int typeId;

	@Column(name="item_id", insertable=false, updatable=false, unique=true, nullable=false)
	private int itemId;

}