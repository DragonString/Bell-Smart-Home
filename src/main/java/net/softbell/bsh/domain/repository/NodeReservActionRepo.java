package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.NodeReservAction;
import net.softbell.bsh.domain.entity.NodeReservActionPK;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 예약 액션 리포지토리 인터페이스
 */
@Repository
public interface NodeReservActionRepo extends JpaRepository<NodeReservAction, NodeReservActionPK>
{
	
}
