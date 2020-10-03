package net.softbell.bsh.domain.repository

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.GroupPermission
import net.softbell.bsh.domain.entity.MemberGroup
import net.softbell.bsh.domain.entity.MemberGroupItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @author : Bell(bell@softbell.net)
 * @description : 회원 그룹 리포지토리 인터페이스
 */
@Repository
interface MemberGroupRepo : JpaRepository<MemberGroup, Long> {
    fun findByEnableStatus(enableStatus: EnableStatusRule): List<MemberGroup>
    fun findByMemberGroupItemsInAndEnableStatus(listMemberGroupItem: List<MemberGroupItem>, enableStatus: EnableStatusRule): List<MemberGroup>
    fun findByGroupPermissionsIn(listGroupPermission: List<GroupPermission>): List<MemberGroup>
    fun findByMemberGroupIdInAndGroupPermissionsIn(listMemberGroupId: List<Long>, listGroupPermission: List<GroupPermission>): List<MemberGroup>
}
