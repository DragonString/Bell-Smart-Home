package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.GroupPermission;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 그룹 권한 리포지토리 인터페이스
 */
@Repository
public interface GroupPermissionRepo extends JpaRepository<GroupPermission, Long>
{
	
}
