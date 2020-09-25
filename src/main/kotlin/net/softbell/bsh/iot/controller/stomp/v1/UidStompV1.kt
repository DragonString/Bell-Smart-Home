package net.softbell.bsh.iot.controller.stomp.v1

import lombok.AllArgsConstructor
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto
import net.softbell.bsh.iot.service.v1.IotUIDServiceV1
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.Throws

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP UID Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@AllArgsConstructor
@RestController
class UidStompV1 {
    // Global Field
    private val iotUIDServiceV1: IotUIDServiceV1? = null
    @MessageMapping("/iot/v1/node/uid/{uid}/SET/INFO/NODE")
    fun NodeHandlerNewNode(@DestinationVariable("uid") uid: String?, nodeInfo: NodeInfoV1Dto) {
        iotUIDServiceV1!!.setNewNodeInfo(uid, nodeInfo)
    }

    @MessageMapping("/iot/v1/node/uid/{uid}/GET/INFO/TOKEN")
    fun NodeHandlerRegister(@DestinationVariable("uid") uid: String) {
        iotUIDServiceV1!!.generateToken(uid)
    }
}