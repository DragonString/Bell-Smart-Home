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
 * @Description : 그룹 권한 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="group_permission")
@NamedQuery(name="GroupPermission.findAll", query="SELECT g FROM GroupPermission g")
public class GroupPermission implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private GroupPermissionPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="assign_date", nullable=false)
	private Date assignDate;

	@Column(name="group_permission", nullable=false)
	private byte groupPermission;

	@ManyToOne
	@JoinColumn(name="node_group_id", nullable=false, insertable=false, updatable=false)
	private NodeGroup nodeGroup;

	@ManyToOne
	@JoinColumn(name="member_group_id", nullable=false, insertable=false, updatable=false)
	private MemberGroup memberGroup;
}