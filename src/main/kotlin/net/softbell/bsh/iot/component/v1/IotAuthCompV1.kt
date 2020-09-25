package net.softbell.bsh.iot.component.v1

import lombok.extern.slf4j.Slf4j
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.repository.NodeRepo
import net.softbell.bsh.util.BellLog
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 인증 컴포넌트 v1
 */
@Slf4j
@Component
class IotAuthCompV1 {
    @Autowired
    private val nodeRepo: NodeRepo? = null

    // Field
    val randomToken: String

    // Generate

        // Return
        get() {
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
        token = randomToken
        node.setToken(token)

        // DB - Save
        nodeRepo.save(node)

        // Log
        log.info(BellLog.getLogHead() + "보안 토큰 생성 (" + uid + "->" + token + ")")

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
        val enableStatus: EnableStatusRule = node.getEnableStatus()

        // Process
        return if (enableStatus == EnableStatusRule.WAIT || enableStatus == EnableStatusRule.REJECT) false else true
    }

    companion object {
        // Global Field
        private val secureRandom = SecureRandom() //threadsafe
        private val base64Encoder = Base64.getUrlEncoder() //threadsafe
    }
}