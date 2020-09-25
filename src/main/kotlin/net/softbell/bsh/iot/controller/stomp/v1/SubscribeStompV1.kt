package net.softbell.bsh.iot.controller.stomp.v1

import lombok.AllArgsConstructor
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto
import net.softbell.bsh.iot.service.v1.IotSubscribeServiceV1
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.Throws

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP Subscribe Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@AllArgsConstructor
@RestController
class SubscribeStompV1 {
    // Global Field
    private val iotSubscribeServiceV1: IotSubscribeServiceV1? = null
    @SubscribeMapping("/iot/v1/node")
    fun NodeTopicSubscribeHandler(): BaseV1Dto? {
        return iotSubscribeServiceV1!!.procTopicSubscribe()
    }

    @SubscribeMapping("/iot/v1/node/uid/{uid}")
    fun NodeUIDSubscribeHandler(@DestinationVariable("uid") uid: String): BaseV1Dto? {
        return iotSubscribeServiceV1!!.procUIDSubscribe(uid)
    }

    @SubscribeMapping("/iot/v1/node/token/{token}")
    fun NodeTokenSubscribeHandler(@DestinationVariable("token") token: String): BaseV1Dto? {
        return iotSubscribeServiceV1!!.procTokenSubscribe(token)
    }
}