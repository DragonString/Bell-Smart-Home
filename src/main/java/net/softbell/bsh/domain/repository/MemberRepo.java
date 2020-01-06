package net.softbell.bsh.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.softbell.bsh.domain.entity.Member;

@Repository
public interface MemberRepo extends JpaRepository<Member, Long>
{
	Optional<Member> findByUserId(String userId);
}
