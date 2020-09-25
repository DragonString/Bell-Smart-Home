package net.softbell.bsh.service

import lombok.AllArgsConstructor
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberInterlockToken
import net.softbell.bsh.domain.repository.MemberInterlockTokenRepo
import net.softbell.bsh.dto.request.InterlockTokenDto
import net.softbell.bsh.iot.component.v1.IotAuthCompV1
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 연동 서비스
 */
@AllArgsConstructor
@Service
class InterlockService constructor() {
    // Global Field
    private val memberService: MemberService? = null
    private val authComp: IotAuthCompV1? = null
    private val memberInterlockTokenRepo: MemberInterlockTokenRepo? = null
    fun getAllTokens(auth: Authentication): List<MemberInterlockToken?>? {
        // Field
        val member: Member?

        // Init
        member = memberService!!.getMember(auth.getName())

        // Return
        return memberInterlockTokenRepo!!.findByMember(member)
    }

    fun findEnableTokenToMember(token: String?): Member? {
        // Field
        val memberInterlockToken: MemberInterlockToken?

        // Init
        memberInterlockToken = memberInterlockTokenRepo!!.findByEnableStatusAndToken(EnableStatusRule.ENABLE, token)

        // Exception
        if (memberInterlockToken == null) return null

        // Return
        return memberInterlockToken.getMember()
    }

    fun findTokenToMember(token: String?): Member {
        // Field
        val memberInterlockToken: MemberInterlockToken?

        // Init
        memberInterlockToken = memberInterlockTokenRepo!!.findByToken(token)

        // Return
        return memberInterlockToken.getMember()
    }

    @Transactional
    fun createToken(auth: Authentication, interlockTokenDto: InterlockTokenDto): Boolean {
        // Field
        val member: Member?
        val memberInterlockToken: MemberInterlockToken

        // Init
        member = memberService!!.getMember(auth.getName())

        // Exception
        if (member == null) return false

        // Process
        memberInterlockToken = builder()
                .name(interlockTokenDto.getName())
                .member(member)
                .registerDate(Date())
                .enableStatus(EnableStatusRule.ENABLE)
                .token(authComp.getRandomToken())
                .build()

        // DB - Save
        memberInterlockTokenRepo!!.save(memberInterlockToken)

        // Return
        return true
    }

    @Transactional
    fun modifyToken(auth: Authentication, tokenId: Long, enableStatus: EnableStatusRule?): Boolean {
        // Field
        val member: Member?
        val optMemberInterlockToken: Optional<MemberInterlockToken?>

        // Init
        member = memberService!!.getMember(auth.getName())
        optMemberInterlockToken = memberInterlockTokenRepo!!.findById(tokenId)

        // Exception
        if (!optMemberInterlockToken.isPresent() || member == null) return false

        // DB - Update
        optMemberInterlockToken.get().setEnableStatus(enableStatus)

        // Return
        return true
    }

    @Transactional
    fun deleteToken(auth: Authentication, tokenId: Long): Boolean {
        // Field
        val member: Member?
        val optMemberInterlockToken: Optional<MemberInterlockToken?>

        // Init
        member = memberService!!.getMember(auth.getName())
        optMemberInterlockToken = memberInterlockTokenRepo!!.findById(tokenId)

        // Exception
        if (!optMemberInterlockToken.isPresent() || member == null) return false

        // DB - Update
        memberInterlockTokenRepo.delete(optMemberInterlockToken.get())

        // Return
        return true
    }
}