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
 * @Description : 노드 트리거 아이템 주 키
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class NodeTriggerItemPK implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Column(name="item_id", insertable=false, updatable=false, unique=true, nullable=false)
	private long itemId;

	@Column(name="trigger_id", insertable=false, updatable=false, unique=true, nullable=false)
	private long triggerId;
}