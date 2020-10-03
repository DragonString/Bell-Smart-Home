package net.softbell.bsh.service

import net.softbell.bsh.component.PermissionComp
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.GroupRole
import net.softbell.bsh.domain.MemberRole
import net.softbell.bsh.domain.entity.*
import net.softbell.bsh.domain.repository.*
import net.softbell.bsh.dto.request.MemberGroupDto
import net.softbell.bsh.dto.request.MemberGroupPermissionDto
import net.softbell.bsh.dto.request.NodeGroupDto
import net.softbell.bsh.dto.request.NodeGroupPermissionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : 권한 서비스
 */
@Service
class PermissionService {
    // Global Field
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var permissionComp: PermissionComp
    @Autowired private lateinit var groupPermissionRepo: GroupPermissionRepo
    @Autowired private lateinit var nodeGroupRepo: NodeGroupRepo
    @Autowired private lateinit var nodeGroupItemRepo: NodeGroupItemRepo
    @Autowired private lateinit var memberGroupRepo: MemberGroupRepo
    @Autowired private lateinit var memberGroupItemRepo: MemberGroupItemRepo
    @Autowired private lateinit var nodeRepo: NodeRepo

    fun getAllMemberGroup(): List<MemberGroup> {
        return memberGroupRepo.findAll()
    }

    fun getAllNodeGroup(): List<NodeGroup> {
        return nodeGroupRepo.findAll()
    }

    fun getMemberGroup(gid: Long): MemberGroup? {
        // Init
        val optMemberGroup: Optional<MemberGroup> = memberGroupRepo.findById(gid)

        // Return
        return if (optMemberGroup.isPresent)
            optMemberGroup.get()
        else
            null
    }

    fun getNodeGroup(gid: Long): NodeGroup? {
        // Init
        val optNodeGroup: Optional<NodeGroup> = nodeGroupRepo.findById(gid)

        // Return
        return if (optNodeGroup.isPresent)
            optNodeGroup.get()
        else
            null
    }

    fun isPrivilege(role: GroupRole, auth: Authentication, node: Node): Boolean {
        // Init
        val member: Member = memberService.getMember(auth.name) ?: return false

        // Return
        return isPrivilege(role, member, node)
    }

    /** @description 특정 사용자가 해당 노드에 권한이 있는지 검증
     * 1. 사용자가 포함된 활성화된 사용자 그룹 리스트 검색
     * 2. 노드가 포함된 활성화된 노드 그룹 리스트 검색
     * 3. 사용자 그룹과 노드 그룹과 권한으로 그룹 권한이 있는지 검색
     * 4. 권한이 있으면 true
     */
    fun isPrivilege(role: GroupRole, member: Member, node: Node): Boolean {
        if (member.permission == MemberRole.ADMIN || member.permission == MemberRole.SUPERADMIN)
            return true // 관리자는 모든 권한 통과

        // Init
        val listMemberGroup = permissionComp.getEnableMemberGroup(member) // 권한 있는 사용자 그룹 로드
        val listNodeGroup = permissionComp.getEnableNodeGroup(node) // 권한 있는 노드 그룹 로드
        val listGroupPermission = permissionComp.getGroupPermission(role, listMemberGroup, listNodeGroup) // 요청한 권한 로드

        // Return
        return listGroupPermission.isNotEmpty()
    }

    fun getPrivilegeNodeGroupItems(role: GroupRole, auth: Authentication): List<NodeGroupItem> {
        // Init
        val member = memberService.getMember(auth.name) ?: return emptyList()

        // Return
        return getPrivilegeNodeGroupItems(role, member)
    }

    /** @description 특정 회원에게 특정 권한이 있는 노드 리스트 반환
     * 1. 사용자가 포함된 활성화된 사용자 그룹 리스트 검색
     * 2. 사용자 그룹과 권한으로 그룹 권한 검색
     * 3. 그룹 권한으로 활성화된 노드 그룹 리스트 검색
     * 4. 검색된 노드 그룹에 연결된 노드 그룹 아이템으로 노드 리스트 검색
     */
    fun getPrivilegeNodeGroupItems(role: GroupRole, member: Member): List<NodeGroupItem> {
        // Init
        val listMemberGroup = permissionComp.getEnableMemberGroup(member) // 권한 있는 사용자 그룹 로드
        val listGroupPermission = permissionComp.getMemberGroupPermission(role, listMemberGroup) // 요청한 권한 로드
        val listNodeGroup = permissionComp.getEnableNodeGroup() // 활성화된 노드 그룹 로드
        val listPrivilegeNodeGroup = permissionComp.getPrivilegeNodeGroup(listNodeGroup, listGroupPermission) // 권한있는 노드 그룹 로드
        val listPrivilegeNodeGroupItem: MutableList<NodeGroupItem> = ArrayList()

        // Process
        for (entity in listPrivilegeNodeGroup)
            listPrivilegeNodeGroupItem.addAll(entity.nodeGroupItems)

        // Return
        return listPrivilegeNodeGroupItem
    }

    @Transactional
    fun createMemberGroup(memberGroupDto: MemberGroupDto): Boolean {
        // Parent Load
        val memberGroup = MemberGroup(
                name = memberGroupDto.name,
                enableStatus = EnableStatusRule.DISABLE
        )
        if (memberGroupDto.enableStatus)
            memberGroup.enableStatus = EnableStatusRule.ENABLE

        // DB - Save
        memberGroupRepo.save(memberGroup)

        // Child Load
        for (memberId in memberGroupDto.memberId) {
            // Field
            val memberGroupItem: MemberGroupItem

            // Init
            val member: Member = memberService.getMember(memberId) ?: continue

            // Create
            memberGroupItem = MemberGroupItem(
                    memberGroup = memberGroup,
                    member = member,
                    assignDate = Date()
            )

            // DB = Save
            memberGroupItemRepo.save(memberGroupItem)
        }

        // Return
        return true
    }

    @Transactional
    fun modifyMemberGroup(gid: Long, memberGroupDto: MemberGroupDto): Boolean {
        // Init
        val memberGroup: MemberGroup
        val optMemberGroup = memberGroupRepo.findById(gid)

        // Exception
        if (!optMemberGroup.isPresent) return false

        // Parent Load
        memberGroup = optMemberGroup.get()

        // DB - Update
        memberGroup.name = memberGroupDto.name
        if (memberGroupDto.enableStatus)
            memberGroup.enableStatus = EnableStatusRule.ENABLE
        else
            memberGroup.enableStatus = EnableStatusRule.DISABLE

        // DB - Delete
        memberGroupItemRepo.deleteAll(memberGroup.memberGroupItems)
        memberGroupItemRepo.flush()

        // Child Load
        for (memberId in memberGroupDto.memberId) {
            // Init
            val member = memberService.getMember(memberId) ?: continue

            // Create
            val memberGroupItem = MemberGroupItem(
                    memberGroup = memberGroup,
                    member = member,
                    assignDate = Date()
            )

            // DB = Save
            memberGroupItemRepo.save(memberGroupItem)
        }

        // Return
        return true
    }

    @Transactional
    fun enableMemberGroup(listGid: List<Long>): Boolean {
        // Init
        var isSuccess = true

        // Process
        for (gid in listGid) {
            // Init
            val optMemberGroup = memberGroupRepo.findById(gid)

            // Exception
            if (!optMemberGroup.isPresent) {
                isSuccess = false
                continue
            }

            // DB - Update
            optMemberGroup.get().enableStatus = EnableStatusRule.ENABLE
        }

        // Return
        return isSuccess
    }

    @Transactional
    fun disableMemberGroup(listGid: List<Long>): Boolean {
        // Init
        var isSuccess = true

        // Process
        for (gid in listGid) {
            // Init
            val optMemberGroup = memberGroupRepo.findById(gid)

            // Exception
            if (!optMemberGroup.isPresent) {
                isSuccess = false
                continue
            }

            // DB - Update
            optMemberGroup.get().enableStatus = EnableStatusRule.DISABLE
        }

        // Return
        return isSuccess
    }

    @Transactional
    fun deleteMemberGroup(listGid: List<Long>): Boolean {
        // Init
        var isSuccess = true

        // Process
        for (gid in listGid) {
            // Init
            val optMemberGroup = memberGroupRepo.findById(gid)

            // Exception
            if (!optMemberGroup.isPresent) {
                isSuccess = false
                continue
            }

            // Load
            val memberGroup = optMemberGroup.get()

            // DB - Delete
            groupPermissionRepo.deleteAll(memberGroup.groupPermissions)
            memberGroupItemRepo.deleteAll(memberGroup.memberGroupItems)
            memberGroupRepo.delete(memberGroup)
        }

        // Return
        return isSuccess
    }

    @Transactional
    fun addMemberPermission(gid: Long, memberGroupPermissionDto: MemberGroupPermissionDto): Boolean {
        // Init
        val memberGroup = getMemberGroup(gid) ?: return false
        val nodeGroup = getNodeGroup(memberGroupPermissionDto.nodeGid) ?: return false
        val groupRole = GroupRole.ofLegacyCode(memberGroupPermissionDto.permission) ?: return false

        // TODO 기존 권한이 있으면 생성 중단하는 코드 필요

        // Make
        val groupPermission = GroupPermission(
                assignDate = Date(),
                memberGroup = memberGroup,
                nodeGroup = nodeGroup,
                groupPermission = groupRole
        )

        // DB - Save
        groupPermissionRepo.save(groupPermission)

        // Return
        return true
    }

    @Transactional
    fun createNodeGroup(nodeGroupDto: NodeGroupDto): Boolean {
        // Parent Load
        val nodeGroup = NodeGroup(
                name = nodeGroupDto.name,
                enableStatus = EnableStatusRule.DISABLE
        )
        if (nodeGroupDto.enableStatus)
            nodeGroup.enableStatus = EnableStatusRule.ENABLE

        // DB - Save
        nodeGroupRepo.save(nodeGroup)

        // Child Load
        for (nodeId in nodeGroupDto.nodeId) {
            // Init
            val optNode = nodeRepo.findById(nodeId)

            // Exception
            if (!optNode.isPresent)
                continue

            // Create
            val nodeGroupItem = NodeGroupItem(
                    nodeGroup = nodeGroup,
                    node = optNode.get(),
                    assignDate = Date()
            )

            // DB = Save
            nodeGroupItemRepo.save(nodeGroupItem)
        }

        // Return
        return true
    }

    @Transactional
    fun modifyNodeGroup(gid: Long, nodeGroupDto: NodeGroupDto): Boolean {
        // Init
        val optNodeGroup = nodeGroupRepo.findById(gid)

        // Exception
        if (!optNodeGroup.isPresent)
            return false

        // Parent Load
        val nodeGroup = optNodeGroup.get()

        // DB - Modify
        nodeGroup.name = nodeGroupDto.name
        if (nodeGroupDto.enableStatus)
            nodeGroup.enableStatus = EnableStatusRule.ENABLE
        else
            nodeGroup.enableStatus = EnableStatusRule.DISABLE

        // DB - Delete
        nodeGroupItemRepo.deleteAll(nodeGroup.nodeGroupItems)
        nodeGroupItemRepo.flush()

        // Child Load
        for (nodeId in nodeGroupDto.nodeId) {
            // Init
            val optNode = nodeRepo.findById(nodeId)

            // Exception
            if (!optNode.isPresent)
                continue

            // Create
            val nodeGroupItem = NodeGroupItem(
                    nodeGroup = nodeGroup,
                    node = optNode.get(),
                    assignDate = Date()
            )

            // DB = Save
            nodeGroupItemRepo.save(nodeGroupItem)
        }

        // Return
        return true
    }

    @Transactional
    fun enableNodeGroup(listGid: List<Long>): Boolean {
        // Init
        var isSuccess = true

        // Process
        for (gid in listGid) {
            // Init
            val optNodeGroup = nodeGroupRepo.findById(gid)

            // Exception
            if (!optNodeGroup.isPresent) {
                isSuccess = false
                continue
            }

            // DB - Update
            optNodeGroup.get().enableStatus = EnableStatusRule.ENABLE
        }

        // Return
        return isSuccess
    }

    @Transactional
    fun disableNodeGroup(listGid: List<Long>): Boolean {
        // Init
        var isSuccess = true

        // Process
        for (gid in listGid) {
            // Init
            val optNodeGroup = nodeGroupRepo.findById(gid)

            // Exception
            if (!optNodeGroup.isPresent) {
                isSuccess = false
                continue
            }

            // DB - Update
            optNodeGroup.get().enableStatus = EnableStatusRule.DISABLE
        }

        // Return
        return isSuccess
    }

    @Transactional
    fun deleteNodeGroup(listGid: List<Long>): Boolean {
        // Init
        var isSuccess = true

        // Process
        for (gid in listGid) {
            // Init
            val optNodeGroup = nodeGroupRepo.findById(gid)

            // Exception
            if (!optNodeGroup.isPresent) {
                isSuccess = false
                continue
            }

            // Load
            val nodeGroup = optNodeGroup.get()

            // DB - Delete
            groupPermissionRepo.deleteAll(nodeGroup.groupPermissions)
            nodeGroupItemRepo.deleteAll(nodeGroup.nodeGroupItems)
            nodeGroupRepo.delete(nodeGroup)
        }

        // Return
        return isSuccess
    }

    @Transactional
    fun addNodePermission(gid: Long, nodeGroupPermissionDto: NodeGroupPermissionDto): Boolean {
        // Init
        val memberGroup = getMemberGroup(nodeGroupPermissionDto.memberGid) ?: return false
        val nodeGroup = getNodeGroup(gid) ?: return false
        val groupRole = GroupRole.ofLegacyCode(nodeGroupPermissionDto.permission) ?: return false

        // TODO 기존 권한이 있으면 생성 중단하는 코드 필요

        // Make
        val groupPermission = GroupPermission(
                assignDate = Date(),
                memberGroup = memberGroup,
                nodeGroup = nodeGroup,
                groupPermission = groupRole
        )

        // DB - Save
        groupPermissionRepo.save(groupPermission)

        // Return
        return true
    }

    @Transactional
    fun deleteGroupPermission(gid: Long): Boolean {
        // Init
        val optGroupPermission = groupPermissionRepo.findById(gid)

        // Exception
        if (!optGroupPermission.isPresent)
            return false

        // DB - Save
        groupPermissionRepo.delete(optGroupPermission.get())

        // Return
        return true
    }
}