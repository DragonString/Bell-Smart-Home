package net.softbell.bsh.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.softbell.bsh.domain.BanRule;
import net.softbell.bsh.domain.MemberRole;


/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 엔티티
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member")
@NamedQuery(name="Member.findAll", query="SELECT m FROM Member m")
public class Member implements Serializable, UserDetails 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="member_id", unique=true, nullable=false)
	private Long memberId;

	@Column(nullable=false)
	private BanRule ban;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ban_date")
	private Date banDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_fail_ban_start")
	private Date loginFailBanStart;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="change_passwd_date")
	private Date changePasswdDate;

	@Column(name="email", nullable=false, length=200)
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_login")
	private Date lastLogin;

	@Column(nullable=false, length=40)
	private String nickname;

	@Column(nullable=false, length=64)
	private String password;

	@Column(nullable=false)
	private MemberRole permission;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_date", nullable=false)
	private Date registerDate;

	@Column(name="user_id", nullable=false, length=50)
	private String userId;

	@Column(nullable=false, length=10)
	private String name;

	@OneToMany(mappedBy="member")
	private List<MemberGroupItem> memberGroupItems;

	@OneToMany(mappedBy="member")
	private List<MemberLoginLog> memberLoginLogs;

	@OneToMany(mappedBy="member")
	private List<NodeAction> nodeActions;

	@OneToMany(mappedBy="member")
	private List<NodeReserv> nodeReservs;

	@OneToMany(mappedBy="member")
	private List<NodeTrigger> nodeTriggers;

	public MemberGroupItem addMemberGroupItem(MemberGroupItem memberGroupItem)
	{
		getMemberGroupItems().add(memberGroupItem);
		memberGroupItem.setMember(this);

		return memberGroupItem;
	}

	public MemberGroupItem removeMemberGroupItem(MemberGroupItem memberGroupItem)
	{
		getMemberGroupItems().remove(memberGroupItem);
		memberGroupItem.setMember(null);

		return memberGroupItem;
	}

	public MemberLoginLog addMemberLoginLog(MemberLoginLog memberLoginLog)
	{
		getMemberLoginLogs().add(memberLoginLog);
		memberLoginLog.setMember(this);

		return memberLoginLog;
	}

	public MemberLoginLog removeMemberLoginLog(MemberLoginLog memberLoginLog)
	{
		getMemberLoginLogs().remove(memberLoginLog);
		memberLoginLog.setMember(null);

		return memberLoginLog;
	}

	public NodeAction addNodeAction(NodeAction nodeAction)
	{
		getNodeActions().add(nodeAction);
		nodeAction.setMember(this);

		return nodeAction;
	}

	public NodeAction removeNodeAction(NodeAction nodeAction)
	{
		getNodeActions().remove(nodeAction);
		nodeAction.setMember(null);

		return nodeAction;
	}

	public NodeReserv addNodeReserv(NodeReserv nodeReserv)
	{
		getNodeReservs().add(nodeReserv);
		nodeReserv.setMember(this);

		return nodeReserv;
	}

	public NodeReserv removeNodeReserv(NodeReserv nodeReserv)
	{
		getNodeReservs().remove(nodeReserv);
		nodeReserv.setMember(null);

		return nodeReserv;
	}

	public NodeTrigger addNodeTrigger(NodeTrigger nodeTrigger)
	{
		getNodeTriggers().add(nodeTrigger);
		nodeTrigger.setMember(this);

		return nodeTrigger;
	}

	public NodeTrigger removeNodeTrigger(NodeTrigger nodeTrigger)
	{
		getNodeTriggers().remove(nodeTrigger);
		nodeTrigger.setMember(null);

		return nodeTrigger;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		// Field
		List<GrantedAuthority> listAuth = new ArrayList<GrantedAuthority>();
		
		// Load
		listAuth.add(new SimpleGrantedAuthority(permission.getValue()));
		
		// Return
		return listAuth;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		if (this.ban == BanRule.NORMAL)
			return true;
		return false;
	}

	@Override
	public String getUsername()
	{
		return getUserId();
	}
}