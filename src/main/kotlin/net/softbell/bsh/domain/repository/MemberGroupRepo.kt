package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.GroupPermission;
import net.softbell.bsh.domain.entity.MemberGroup;
import net.softbell.bsh.domain.entity.MemberGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 리포지토리 인터페이스
 */
@Repository
public interface MemberGroupRepo extends JpaRepository<MemberGroup, Long>
{
	List<MemberGroup> findByEnableStatus(EnableStatusRule enableStatus);
	List<MemberGroup> findByMemberGroupItemsInAndEnableStatus(List<MemberGroupItem> listMemberGroupItem, EnableStatusRule enableStatus);
	List<MemberGroup> findByGroupPermissionsIn(List<GroupPermission> listGroupPermission);
	List<MemberGroup> findByMemberGroupIdInAndGroupPermissionsIn(List<Long> listMemberGroupId, List<GroupPermission> listGroupPermission);
}
