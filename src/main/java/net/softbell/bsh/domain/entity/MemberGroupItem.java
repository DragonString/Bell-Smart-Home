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
 * @Description : 회원 그룹 아이템 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member_group_item")
@NamedQuery(name="MemberGroupItem.findAll", query="SELECT m FROM MemberGroupItem m")
public class MemberGroupItem
{
	@EmbeddedId
	private MemberGroupItemPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="assign_date", nullable=false)
	private Date assignDate;

	@ManyToOne
	@JoinColumn(name="member_group_id", nullable=false, insertable=false, updatable=false)
	private MemberGroup memberGroup;

	@ManyToOne
	@JoinColumn(name="member_id", nullable=false, insertable=false, updatable=false)
	private Member member;
}