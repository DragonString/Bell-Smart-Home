package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
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
 * @Description : 노드 아이템 기록 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_item_history")
@NamedQuery(name="NodeItemHistory.findAll", query="SELECT n FROM NodeItemHistory n")
public class NodeItemHistory implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private NodeItemHistoryPK id;

	@Column(name="pin_status", nullable=false)
	private short pinStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="receive_date", nullable=false)
	private Date receiveDate;

	@ManyToOne
	@JoinColumn(name="item_id", nullable=false, insertable=false, updatable=false)
	private NodeItem nodeItem;
}