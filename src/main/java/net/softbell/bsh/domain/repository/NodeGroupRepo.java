package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.NodeGroup;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 그룹 리포지토리 인터페이스
 */
@Repository
public interface NodeGroupRepo extends JpaRepository<NodeGroup, Long>
{
	
}
