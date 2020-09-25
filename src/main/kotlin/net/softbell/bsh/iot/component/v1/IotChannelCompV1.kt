package net.softbell.bsh.iot.component.v1

import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 채널 컴포넌트 v1
 */
@Component
class IotChannelCompV1 {
    // Global Field
    private val G_UID_URL = "/api/stomp/queue/iot/v1/node/uid/"
    private val G_TOKEN_URL = "/api/stomp/queue/iot/v1/node/token/"

    @Autowired lateinit var template: SimpMessagingTemplate

    fun sendDataUID(data: BaseV1Dto) {
        // Process
        template!!.convertAndSend(G_UID_URL + data.getTarget(), data)
    }

    fun sendDataToken(data: BaseV1Dto?) {
        // Process
        template!!.convertAndSend(G_TOKEN_URL + data.getTarget(), data!!)
    }
}