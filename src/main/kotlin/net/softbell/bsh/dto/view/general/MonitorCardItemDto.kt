package net.softbell.bsh.dto.view.general

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터뷰 카드 아이템 정보 DTO
 */
@Getter
@Setter
class MonitorCardItemDto(entity: NodeItem?, lastHistory: NodeItemHistory?) {
    private val alias: String
    private val lastStatus: Double
    private var widthPercent: Int? = null
    private var isDigital: Boolean? = null

    init {
        // Exception
        if (entity == null) return

        // Convert
        alias = entity.getAlias()
        lastStatus = lastHistory.getItemStatus()
        when (entity.getItemMode()) {
            DIGITAL -> {
                isDigital = true
                if (lastStatus == 0.0) widthPercent = 0 else widthPercent = 100
            }
            ANALOG -> {
                isDigital = false
                when (entity.getItemType()) {
                    SENSOR_HUMIDITY -> widthPercent = lastStatus.toInt() // 습도는 0~100%
                    SENSOR_TEMPERATURE -> widthPercent = (lastStatus + 20).toInt() // 온도는 -20~40
                    READER_RFID -> if (lastStatus != 0.0) widthPercent = 100 // RFID는 탐지 여부로 판정
                    else widthPercent = 0
                    else -> widthPercent = (lastStatus / 10).toInt()
                }
            }
            else -> widthPercent = 0
        }
    }
}