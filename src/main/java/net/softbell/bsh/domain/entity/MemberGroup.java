package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member_group")
@NamedQuery(name="MemberGroup.findAll", query="SELECT m FROM MemberGroup m")
public class MemberGroup implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_group_id", unique=true, nullable=false)
	private int memberGroupId;

	@Column(name="enable_status", nullable=false)
	private byte enableStatus;

	@Column(nullable=false, length=50)
	private String name;

	@Column(nullable=false)
	private byte type;

	@OneToMany(mappedBy="memberGroup")
	private List<GroupPermission> groupPermissions;

	@OneToMany(mappedBy="memberGroup")
	private List<MemberGroupItem> memberGroupItems;

	public GroupPermission addGroupPermission(GroupPermission groupPermission)
	{
		getGroupPermissions().add(groupPermission);
		groupPermission.setMemberGroup(this);

		return groupPermission;
	}

	public GroupPermission removeGroupPermission(GroupPermission groupPermission)
	{
		getGroupPermissions().remove(groupPermission);
		groupPermission.setMemberGroup(null);

		return groupPermission;
	}

	public MemberGroupItem addMemberGroupItem(MemberGroupItem memberGroupItem)
	{
		getMemberGroupItems().add(memberGroupItem);
		memberGroupItem.setMemberGroup(this);

		return memberGroupItem;
	}

	public MemberGroupItem removeMemberGroupItem(MemberGroupItem memberGroupItem)
	{
		getMemberGroupItems().remove(memberGroupItem);
		memberGroupItem.setMemberGroup(null);

		return memberGroupItem;
	}
}