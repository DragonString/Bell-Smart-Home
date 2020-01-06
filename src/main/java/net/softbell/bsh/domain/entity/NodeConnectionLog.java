package net.softbell.bsh.domain.entity;

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
 * @Description : 노드 연결 로그 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="node_connection_log")
@NamedQuery(name="NodeConnectionLog.findAll", query="SELECT n FROM NodeConnectionLog n")
public class NodeConnectionLog
{
	@EmbeddedId
	private NodeConnectionLogPK id;

	@Column(nullable=false, length=15)
	private String ipv4;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="request_date", nullable=false)
	private Date requestDate;

	@Column(nullable=false)
	private byte status;

	@ManyToOne
	@JoinColumn(name="node_id", nullable=false, insertable=false, updatable=false)
	private Node node;
}