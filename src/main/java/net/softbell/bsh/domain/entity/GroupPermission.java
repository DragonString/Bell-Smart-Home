package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.softbell.bsh.domain.GroupRole;


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
@Table(name="group_permission",
	uniqueConstraints={
		@UniqueConstraint(columnNames={"node_group_id", "member_group_id", "group_permission"})
	})
@NamedQuery(name="GroupPermission.findAll", query="SELECT g FROM GroupPermission g")
public class GroupPermission implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="group_permission_id", unique=true, nullable=false)
	private Long groupPermissionId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="assign_date", nullable=false)
	private Date assignDate;

	@Column(name="group_permission", nullable=false)
	private GroupRole groupPermission;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="node_group_id", nullable=false)
	private NodeGroup nodeGroup;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="member_group_id", nullable=false)
	private MemberGroup memberGroup;
}