package net.softbell.bsh.db.model;

import java.io.Serializable;
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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member_info")
@NamedQuery(name="MemberInfo.findAll", query="SELECT m FROM MemberInfo m")
public class MemberInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="member_id", unique=true, nullable=false)
	private int memberId;

	@Column(nullable=false, length=18)
	private String id;

	@Column(name="is_admin")
	private byte isAdmin;

	@Column(name="is_ban")
	private short isBan;

	@Column(nullable=false, length=20)
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_time", nullable=false)
	private Date registerTime;

	//bi-directional many-to-one association to MemberAuth
	@OneToMany(mappedBy="memberInfo")
	private List<MemberAuth> memberAuths;

	//bi-directional many-to-one association to MemberLogin
	@OneToMany(mappedBy="memberInfo")
	private List<MemberLogin> memberLogins;

	public MemberAuth addMemberAuth(MemberAuth memberAuth) {
		getMemberAuths().add(memberAuth);
		memberAuth.setMemberInfo(this);

		return memberAuth;
	}

	public MemberAuth removeMemberAuth(MemberAuth memberAuth) {
		getMemberAuths().remove(memberAuth);
		memberAuth.setMemberInfo(null);

		return memberAuth;
	}

	public MemberLogin addMemberLogin(MemberLogin memberLogin) {
		getMemberLogins().add(memberLogin);
		memberLogin.setMemberInfo(this);

		return memberLogin;
	}

	public MemberLogin removeMemberLogin(MemberLogin memberLogin) {
		getMemberLogins().remove(memberLogin);
		memberLogin.setMemberInfo(null);

		return memberLogin;
	}

}