package net.softbell.bsh.db.model;

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


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="member_auth_type")
@NamedQuery(name="MemberAuthType.findAll", query="SELECT m FROM MemberAuthType m")
public class MemberAuthType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="auth_id", unique=true, nullable=false)
	private int authId;

	@Column(name="auth_name", nullable=false, length=20)
	private String authName;

	//bi-directional many-to-one association to MemberAuth
	@OneToMany(mappedBy="memberAuthType")
	private List<MemberAuth> memberAuths;

	public MemberAuth addMemberAuth(MemberAuth memberAuth) {
		getMemberAuths().add(memberAuth);
		memberAuth.setMemberAuthType(this);

		return memberAuth;
	}

	public MemberAuth removeMemberAuth(MemberAuth memberAuth) {
		getMemberAuths().remove(memberAuth);
		memberAuth.setMemberAuthType(null);

		return memberAuth;
	}

}