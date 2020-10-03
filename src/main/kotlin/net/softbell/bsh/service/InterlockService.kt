package net.softbell.bsh.service

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberInterlockToken
import net.softbell.bsh.domain.repository.MemberInterlockTokenRepo
import net.softbell.bsh.dto.request.InterlockTokenDto
import net.softbell.bsh.iot.component.v1.IotAuthCompV1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : 연동 서비스
 */
@Service
class InterlockService {
    // Global Field
    @Autowired private lateinit var memberService: MemberService
    @Autowired private lateinit var authComp: IotAuthCompV1
    @Autowired private lateinit var memberInterlockTokenRepo: MemberInterlockTokenRepo

    fun getAllTokens(auth: Authentication): List<MemberInterlockToken> {
        // Init
        val member = memberService.getMember(auth.name) ?: return emptyList()

        // Return
        return memberInterlockTokenRepo.findByMember(member)
    }

    fun findEnableTokenToMember(token: String): Member? {
        // Init
        val memberInterlockToken = memberInterlockTokenRepo.findByEnableStatusAndToken(EnableStatusRule.ENABLE, token)

        // Return
        return memberInterlockToken?.member
    }

    fun findTokenToMember(token: String): Member? {
        // Init
        val memberInterlockToken = memberInterlockTokenRepo.findByToken(token)

        // Return
        return memberInterlockToken?.member
    }

    @Transactional
    fun createToken(auth: Authentication, interlockTokenDto: InterlockTokenDto): Boolean {
        // Init
        val member: Member = memberService.getMember(auth.name) ?: return false

        // Process
        val memberInterlockToken = MemberInterlockToken(
                name = interlockTokenDto.name,
                member = member,
                registerDate = Date(),
                enableStatus = EnableStatusRule.ENABLE,
                token = authComp.getRandomToken()
        )

        // DB - Save
        memberInterlockTokenRepo.save(memberInterlockToken)

        // Return
        return true
    }

    @Transactional
    fun modifyToken(auth: Authentication, tokenId: Long, enableStatus: EnableStatusRule): Boolean {
        // Init
        memberService.getMember(auth.name) ?: return false
        val optMemberInterlockToken = memberInterlockTokenRepo.findById(tokenId)

        // Exception
        if (!optMemberInterlockToken.isPresent)
            return false

        // DB - Update
        optMemberInterlockToken.get().enableStatus = enableStatus

        // Return
        return true
    }

    @Transactional
    fun deleteToken(auth: Authentication, tokenId: Long): Boolean {
        // Init
        val member = memberService.getMember(auth.name) ?: return false
        val optMemberInterlockToken = memberInterlockTokenRepo.findById(tokenId)

        // Exception
        if (!optMemberInterlockToken.isPresent)
            return false

        // DB - Update
        memberInterlockTokenRepo.delete(optMemberInterlockToken.get())

        // Return
        return true
    }
}