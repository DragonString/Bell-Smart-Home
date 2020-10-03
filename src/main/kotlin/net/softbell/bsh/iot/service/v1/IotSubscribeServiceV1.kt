package net.softbell.bsh.iot.service.v1

import mu.KLogging
import net.softbell.bsh.domain.repository.NodeRepo
import net.softbell.bsh.iot.component.v1.IotChannelCompV1
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 구독 서비스
 */
@Service
class IotSubscribeServiceV1 {
    // Global Field
    @Autowired private lateinit var iotChannelCompV1: IotChannelCompV1
    @Autowired private lateinit var nodeRepo: NodeRepo

    fun procTopicSubscribe(): BaseV1Dto {
        // Init
        val data = BaseV1Dto(
                sender = "SERVER",
                target = "NODE",
                cmd = "INFO",
                type = "CONNECTION",
                obj = "NODE",
                value = "SUCCESS"
        )

        // Log
        logger.info("Node Topic Channel Subscribe")

        // Return
        return data
    }

    fun procUIDSubscribe(uid: String): BaseV1Dto {
        // Init
        val data = BaseV1Dto(
                sender = "SERVER",
                target = uid,
                cmd = "INFO",
                type = "CONNECTION",
                obj = "UID",
                value = "SUCCESS"
        )

        // Log
        logger.info("Node UID Channel Subscribe ($uid)")

        // Return
        return data
    }

    fun procTokenSubscribe(token: String): BaseV1Dto {
        // Init
        val node = nodeRepo.findByToken(token)
        val listMsg: MutableList<BaseV1Dto> = ArrayList()

        listMsg.add(BaseV1Dto(
                sender = "SERVER",
                target = token,
                cmd = "GET",
                type = "INFO",
                obj = "NODE",
                value = null
        ))
        listMsg.add(BaseV1Dto(
                sender = "SERVER",
                target = token,
                cmd = "GET",
                type = "INFO",
                obj = "ITEMS",
                value = null
        ))

        val msgInfo = BaseV1Dto(
                sender = "SERVER",
                target = token,
                cmd = "INFO",
                type = "CONNECTION",
                obj = "TOKEN",
                value = "SUCCESS"
        )

        // Exception
        if (node == null)
            msgInfo.value = "REJECT"

        // Process
        for (message in listMsg)
            iotChannelCompV1.sendDataToken(message)

        // Log
        logger.info("Node Token Channel Subscribe ($token)")

        // Return
        return msgInfo
    }

    companion object : KLogging()
}