package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.NodeConnection;

@Repository
public interface NodeConnectionRepo extends JpaRepository<NodeConnection, Long>
{

}
