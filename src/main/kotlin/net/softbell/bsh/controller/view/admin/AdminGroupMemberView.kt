package net.softbell.bsh.controller.view.admin

import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberGroup
import net.softbell.bsh.domain.entity.MemberGroupItem
import net.softbell.bsh.dto.request.MemberGroupDto
import net.softbell.bsh.dto.request.MemberGroupPermissionDto
import net.softbell.bsh.dto.view.admin.group.MemberGroupInfoCardDto
import net.softbell.bsh.dto.view.admin.group.MemberGroupPermissionCardDto
import net.softbell.bsh.iot.service.v1.IotNodeServiceV1
import net.softbell.bsh.service.MemberService
import net.softbell.bsh.service.PermissionService
import net.softbell.bsh.service.ViewDtoConverterService
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 관리자 회원 그룹 관리 뷰 컨트롤러
 */
@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/admin/group/member")
class AdminGroupMemberView constructor() {
    // Global Field
    private val G_BASE_PATH: String = "services/admin/group"
    private val G_BASE_REDIRECT_URL: String = "redirect:/admin/group/member"
    private val G_LOGOUT_REDIRECT_URL: String = "redirect:/logout"
    private val viewDtoConverterService: ViewDtoConverterService? = null
    private val memberService: MemberService? = null
    private val permissionService: PermissionService? = null
    private val iotNodeService: IotNodeServiceV1? = null
    @GetMapping
    fun dispGroupMember(model: Model): String {
        // Load
        model.addAttribute("listCardGroups", viewDtoConverterService!!.convMemberGroupSummaryCards(permissionService.getAllMemberGroup()))

        // Return
        return G_BASE_PATH + "/MemberGroup"
    }

    @GetMapping("/create")
    fun dispGroupCreate(model: Model): String {
        // Field

        // Init
        model.addAttribute("listCardMembers", viewDtoConverterService!!.convGroupMemberCardItems(memberService.getAllMember()))

        // Return
        return G_BASE_PATH + "/MemberGroupCreate"
    }

    @GetMapping("/modify/{gid}")
    fun dispGroupModify(model: Model, @PathVariable("gid") gid: Long): String {
        // Field
        val listMember: MutableList<Member?>?
        val memberGroup: MemberGroup?

        // Init
        listMember = memberService.getAllMember()
        memberGroup = permissionService!!.getMemberGroup(gid)

        // Process
        for (entity: MemberGroupItem in memberGroup.getMemberGroupItems()) listMember!!.remove(entity.getMember())

        // View
        model.addAttribute("cardGroup", MemberGroupInfoCardDto(permissionService.getMemberGroup(gid)))
        model.addAttribute("listCardMembers", viewDtoConverterService!!.convGroupMemberCardItems(listMember))

        // Return
        return G_BASE_PATH + "/MemberGroupModify"
    }

    @GetMapping("/{gid}")
    fun dispGroup(model: Model, @PathVariable("gid") gid: Long): String {
        // Field

        // Init
        model.addAttribute("cardPermission", MemberGroupPermissionCardDto(permissionService.getAllNodeGroup()))
        model.addAttribute("cardGroup", MemberGroupInfoCardDto(permissionService!!.getMemberGroup(gid)))

        // Return
        return G_BASE_PATH + "/MemberGroupInfo"
    }

    @PostMapping("/create")
    fun procGroupCreate(auth: Authentication?, memberGroupDto: MemberGroupDto): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.createMemberGroup(memberGroupDto)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?err"
    }

    @PostMapping("/modify/{gid}")
    fun procGroupModify(auth: Authentication?, @PathVariable("gid") gid: Long, memberGroupDto: MemberGroupDto): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.modifyMemberGroup(gid, memberGroupDto)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/" + gid else return G_BASE_REDIRECT_URL + "/" + gid + "?err"
    }

    @PostMapping("/enable")
    fun procGroupEnable(auth: Authentication?, @RequestParam("gid") listGid: List<Long>): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.enableMemberGroup(listGid)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?err"
    }

    @PostMapping("/disable")
    fun procGroupDisable(auth: Authentication?, @RequestParam("gid") listGid: List<Long>): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.disableMemberGroup(listGid)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?err"
    }

    @PostMapping("/delete")
    fun procGroupDelete(auth: Authentication?, @RequestParam("gid") listGid: List<Long>): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.deleteMemberGroup(listGid)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL else return G_BASE_REDIRECT_URL + "?err"
    }

    @PostMapping("/permission/add/{gid}")
    fun addPermission(@PathVariable("gid") gid: Long, memberGroupPermissionDto: MemberGroupPermissionDto): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.addMemberPermission(gid, memberGroupPermissionDto)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/" + gid else return G_BASE_REDIRECT_URL + "/" + gid + "?err"
    }

    @PostMapping("/permission/delete/{gid}")
    fun deletePermission(@PathVariable("gid") gid: Long, @RequestParam("pid") pid: Long): String {
        // Field
        val isSuccess: Boolean

        // Init
        isSuccess = permissionService!!.deleteGroupPermission(pid)

        // Return
        if (isSuccess) return G_BASE_REDIRECT_URL + "/" + gid else return G_BASE_REDIRECT_URL + "/" + gid + "?err"
    }
}