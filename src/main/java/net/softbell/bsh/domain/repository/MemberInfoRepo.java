package net.softbell.bsh.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.MemberInfo;

@Repository
public interface MemberInfoRepo extends JpaRepository<MemberInfo, Long>
{
	Optional<MemberInfo> findByUserId(String userId);
}
