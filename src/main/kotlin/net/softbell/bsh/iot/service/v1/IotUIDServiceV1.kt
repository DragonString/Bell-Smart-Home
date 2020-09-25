package net.softbell.bsh.iot.service.v1

import lombok.AllArgsConstructor
import lombok.extern.slf4j.Slf4j
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.repository.NodeRepo
import net.softbell.bsh.iot.component.v1.IotAuthCompV1
import net.softbell.bsh.iot.component.v1.IotChannelCompV1
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto
import net.softbell.bsh.util.BellLog
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT UID 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
class IotUIDServiceV1 {
    // Global Field
    private val iotChannelCompV1: IotChannelCompV1? = null
    private val iotAuthCompV1: IotAuthCompV1? = null
    private val nodeRepo: NodeRepo? = null
    @Transactional
    fun setNewNodeInfo(uid: String?, nodeInfo: NodeInfoV1Dto): Boolean {
        // Field
        val message: BaseV1Dto
        var node: Node?

        // Init
        node = nodeRepo!!.findByUid(uid)

        // Exception
        if (node != null) return false

        // Process
        node = builder().uid(nodeInfo.getUid())
                .controlMode(nodeInfo.getControlMode())
                .nodeName(nodeInfo.getNodeName())
                .alias(nodeInfo.getNodeName())
                .version(nodeInfo.getVersion())
                .registerDate(Date())
                .enableStatus(EnableStatusRule.WAIT)
                .build()

        // DB - Save
        nodeRepo.save(node)

        // Message
        message = builder().sender("SERVER")
                .target(uid)
                .cmd("INFO")
                .type("NEW")
                .obj("NODE")
                .value("SUCCESS")
                .build()
        iotChannelCompV1!!.sendDataUID(message) // Send

        // Log
        log.info(BellLog.getLogHead() + "New Node Info Save (" + nodeInfo.getUid() + ")")

        // Return
        return true
    }

    fun generateToken(uid: String): Boolean {
        // Field
        val message: BaseV1Dto
        val node: Node?
        val token: String?

        // Init
        node = nodeRepo!!.findByUid(uid)

        // Process
        if (node == null) message = builder().sender("SERVER").target(uid)
                .cmd("GET")
                .type("INFO")
                .obj("NODE")
                .build() else {
            token = iotAuthCompV1!!.generateToken(uid)
            message = builder().sender("SERVER").target(uid)
                    .cmd("SET")
                    .type("INFO")
                    .obj("TOKEN")
                    .value(token)
                    .build()
        }

        // Send
        iotChannelCompV1!!.sendDataUID(message)

        // Return
        return true
    }
}