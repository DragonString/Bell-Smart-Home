package net.softbell.bsh.iot.controller.stomp.v1

import net.softbell.bsh.iot.dto.bshp.v1.ItemInfoV1Dto
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto
import net.softbell.bsh.iot.service.v1.IotTokenServiceV1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author : Bell(bell@softbell.net)
 * @Description : STOMP Token Controller
 * STOMP publish: /api/stomp/pub
 * STOMP subscribe: /api/stomp/topic, /api/stomp/queue
 */
@RestController
class TokenStompV1 {
    // Global Field
    @Autowired private lateinit var iotTokenServiceV1: IotTokenServiceV1

    @MessageMapping("/iot/v1/node/token/{token}/SET/INFO/NODE")
    fun NodeHandlerSetInfoNode(@DestinationVariable("token") token: String, nodeInfo: NodeInfoV1Dto) {
        iotTokenServiceV1.setNodeInfo(token, nodeInfo)
    }

    @MessageMapping("/iot/v1/node/token/{token}/SET/INFO/ITEMS")
    fun NodeHandlerSetInfoItems(@DestinationVariable("token") token: String, listItemInfo: List<ItemInfoV1Dto>) {
        for (itemInfo in listItemInfo) {
            iotTokenServiceV1.setItemInfo(token, itemInfo)
            iotTokenServiceV1.reqItemValue(token, itemInfo.itemIndex.toInt())
        }
    }

    @MessageMapping("/iot/v1/node/token/{token}/SET/INFO/ITEM")
    fun NodeHandlerSetInfoItem(@DestinationVariable("token") token: String, itemInfo: ItemInfoV1Dto) {
        iotTokenServiceV1.setItemInfo(token, itemInfo)
    }

    @MessageMapping("/iot/v1/node/token/{token}/SET/VALUE/ITEM")
    fun NodeHandlerSetValueItem(@DestinationVariable("token") token: String, itemValue: ItemValueV1Dto) {
        iotTokenServiceV1.setItemValue(token, itemValue)
    }
}