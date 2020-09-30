package net.softbell.bsh.iot.component.v1

import mu.KLogging
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.repository.NodeRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 인증 컴포넌트 v1
 */
@Component
class IotAuthCompV1 {
    @Autowired private lateinit var nodeRepo: NodeRepo


    fun getRandomToken(): String {
        // Field
        val token: String
        val randomBytes = ByteArray(24)

        // Generate
        secureRandom.nextBytes(randomBytes)
        token = base64Encoder.encodeToString(randomBytes)

        // Return
        return token
    }

    @Transactional
    fun generateToken(uid: String): String? {
        // Field
        val node: Node?
        val token: String

        // Init
        node = nodeRepo!!.findByUid(uid)

        // Exception
        if (node == null) return null

        // Generate
        token = getRandomToken()
        node.token = token

        // DB - Save
        nodeRepo.save(node)

        // Log
        logger.info("보안 토큰 생성 ($uid->$token)")

        // Return
        return token
    }

    fun isTokenAvailable(token: String?): Boolean {
        return if (nodeRepo!!.findByToken(token) == null) false else true
    }

    /**
     * 노드가 승인상태인지 검증
     * @param node: 노드 엔티티
     * @return true: 승인, false: 미승인 혹은 제한
     */
    fun isApprovalNode(node: Node): Boolean {
        // Field
        val enableStatus = node.enableStatus

        // Process
        return if (enableStatus === EnableStatusRule.WAIT || enableStatus === EnableStatusRule.REJECT) false else true
    }

    companion object : KLogging() {
        // Global Field
        private val secureRandom = SecureRandom() // threadsafe
        private val base64Encoder = Base64.getUrlEncoder() // threadsafe
    }
}