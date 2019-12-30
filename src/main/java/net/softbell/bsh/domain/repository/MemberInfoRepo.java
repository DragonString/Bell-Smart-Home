package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.MemberInfo;

@Repository
public interface MemberInfoRepo extends JpaRepository<MemberInfo, Long>
{

}
