package net.softbell.bsh.iot.service.v1

import mu.KLogging
import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.Node
import net.softbell.bsh.domain.repository.NodeRepo
import net.softbell.bsh.iot.component.v1.IotAuthCompV1
import net.softbell.bsh.iot.component.v1.IotChannelCompV1
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT UID 서비스
 */
@Service
class IotUIDServiceV1 {
    // Global Field
    @Autowired private lateinit var iotChannelCompV1: IotChannelCompV1
    @Autowired private lateinit var iotAuthCompV1: IotAuthCompV1
    @Autowired private lateinit var nodeRepo: NodeRepo

    @Transactional
    fun setNewNodeInfo(uid: String, nodeInfo: NodeInfoV1Dto): Boolean {
        // Init
        var node = nodeRepo.findByUid(uid)

        // Exception
        if (node != null)
            return false

        // Process
        node = Node(
                uid = nodeInfo.uid,
                controlMode = nodeInfo.controlMode,
                nodeName = nodeInfo.nodeName,
                alias = nodeInfo.nodeName,
                version = nodeInfo.version,
                registerDate = Date(),
                enableStatus = EnableStatusRule.WAIT
        )

        // DB - Save
        nodeRepo.save(node)

        // Message
        val message = BaseV1Dto(
                sender = "SERVER",
                target = uid,
                cmd = "INFO",
                type = "NEW",
                obj = "NODE",
                value = "SUCCESS"
        )
        iotChannelCompV1.sendDataUID(message) // Send

        // Log
        logger.info("New Node Info Save (" + nodeInfo.uid + ")")

        // Return
        return true
    }

    fun generateToken(uid: String): Boolean {
        // Init
        val message: BaseV1Dto
        val node = nodeRepo.findByUid(uid)

        // Process
        if (node == null)
            message = BaseV1Dto(
                    sender = "SERVER",
                    target = uid,
                    cmd = "GET",
                    type = "INFO",
                    obj = "NODE",
                    value = null
            )
        else {
            val token = iotAuthCompV1.generateToken(uid)
            message = BaseV1Dto(
                    sender = "SERVER",
                    target = uid,
                    cmd = "SET",
                    type = "INFO",
                    obj = "TOKEN",
                    value = token
            )
        }

        // Send
        iotChannelCompV1.sendDataUID(message)

        // Return
        return true
    }

    companion object : KLogging()
}