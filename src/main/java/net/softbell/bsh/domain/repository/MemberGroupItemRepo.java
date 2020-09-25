package net.softbell.bsh.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberGroupItem;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 그룹 아이템 리포지토리 인터페이스
 */
@Repository
public interface MemberGroupItemRepo extends JpaRepository<MemberGroupItem, Long>
{
	List<MemberGroupItem> findByMember(Member member);

	@Transactional
	@Modifying
	@Query("DELETE FROM MemberGroupItem mgi WHERE mgi.member = ?1")
	void deleteByMember(Member member);
}
