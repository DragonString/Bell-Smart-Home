package net.softbell.bsh.iot.component.v1

import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import net.softbell.bsh.domain.entity.NodeReserv
import net.softbell.bsh.iot.service.v1.IotActionServiceV1
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import javax.transaction.Transactional
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 예약 스케줄러 v1
 */
@Slf4j
@RequiredArgsConstructor
@Component
class IotReservSchedulerV1 {
    // Global Field
    private val iotReservParser: IotReservParserV1? = null
    private val iotActionService: IotActionServiceV1? = null
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    fun iotReservTask() {
        // Field
        val listNodeReserv: List<NodeReserv?>?

        // Init
        listNodeReserv = iotReservParser.getEnableReserv()

        // Process
        for (nodeReserv in listNodeReserv!!) {
            // Field
            var isSuccess: Boolean?

            // Parse
            isSuccess = iotReservParser!!.parseEntity(nodeReserv)

            // Process
            if (isSuccess != null && isSuccess == true) {
                log.info("예약 실행 (" + nodeReserv.getDescription().toString() + ")")
                for (nodeAction in iotReservParser.getReservAction(nodeReserv))  // Get Reserv Action
                    iotActionService!!.execAction(nodeAction, nodeReserv.getMember()) // Exec Action
            }
        }
    }
}