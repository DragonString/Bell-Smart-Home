package net.softbell.bsh.service

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Member
import net.softbell.bsh.domain.entity.MemberInterlockToken
import net.softbell.bsh.domain.repository.MemberInterlockTokenRepo
import net.softbell.bsh.dto.request.InterlockTokenDto
import net.softbell.bsh.iot.component.v1.IotAuthCompV1
import org.springframework.beans.factory.annotation.Autowired
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

    fun getAllTokens(member: Member): List<MemberInterlockToken> {
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
    fun createToken(member: Member, interlockTokenDto: InterlockTokenDto): Boolean {
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
    fun modifyToken(member: Member, tokenId: Long, enableStatus: EnableStatusRule): Boolean {
        // Init
        val optMemberInterlockToken = memberInterlockTokenRepo.findById(tokenId)

        // Exception
        if (!optMemberInterlockToken.isPresent)
            return false

        // Load
        val memberInterlockToken = optMemberInterlockToken.get()

        // Privileges Check
        if (!memberService.isAdmin(member) && memberInterlockToken.member != member)
            return false

        // DB - Update
        memberInterlockToken.enableStatus = enableStatus
        memberInterlockTokenRepo.save(memberInterlockToken)

        // Return
        return true
    }

    @Transactional
    fun deleteToken(member: Member, tokenId: Long): Boolean {
        // Init
        val optMemberInterlockToken = memberInterlockTokenRepo.findById(tokenId)

        // Exception
        if (!optMemberInterlockToken.isPresent)
            return false

        // Load
        val memberInterlockToken = optMemberInterlockToken.get()

        // Privileges Check
        if (!memberService.isAdmin(member) && memberInterlockToken.member != member)
            return false

        // DB - Delete
        memberInterlockTokenRepo.delete(memberInterlockToken)

        // Return
        return true
    }
}