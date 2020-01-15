package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="member_group_item",
	uniqueConstraints={
		@UniqueConstraint(columnNames={"member_group_id", "member_id"})
	})
@NamedQuery(name="MemberGroupItem.findAll", query="SELECT m FROM MemberGroupItem m")
public class MemberGroupItem implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="group_item_id", unique=true, nullable=false)
	private long groupItemId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="assign_date", nullable=false)
	private Date assignDate;

	@ManyToOne
	@JoinColumn(name="member_group_id", nullable=false)
	private MemberGroup memberGroup;

	@ManyToOne
	@JoinColumn(name="member_id", nullable=false)
	private Member member;
}