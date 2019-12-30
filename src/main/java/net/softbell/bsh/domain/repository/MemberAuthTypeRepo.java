package net.softbell.bsh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.MemberAuthType;

@Repository
public interface MemberAuthTypeRepo extends JpaRepository<MemberAuthType, Long>
{

}
