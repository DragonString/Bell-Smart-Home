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
 * @Description : 그룹 권한 주 키
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class GroupPermissionPK implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Column(name="node_group_id", insertable=false, updatable=false, unique=true, nullable=false)
	private long nodeGroupId;

	@Column(name="member_group_id", insertable=false, updatable=false, unique=true, nullable=false)
	private long memberGroupId;
}