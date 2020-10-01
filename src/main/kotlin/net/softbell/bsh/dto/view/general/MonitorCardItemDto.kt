package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터뷰 카드 아이템 정보 DTO
 */
class MonitorCardItemDto(entity: NodeItem?, lastHistory: NodeItemHistory) {
    var alias: String?
    var lastStatus: Double?
    var widthPercent: Int? = null
    var digital: Boolean? = null

    init {
        // Exception
        entity.let {
            // Convert
            alias = entity!!.alias
            lastStatus = lastHistory.itemStatus
            when (entity.itemMode) {
                ItemModeRule.DIGITAL -> {
                    digital = true
                    if (lastStatus == 0.0) widthPercent = 0 else widthPercent = 100
                }
                ItemModeRule.ANALOG -> {
                    digital = false
                    when (entity.itemType) {
                        ItemTypeRule.SENSOR_HUMIDITY -> widthPercent = lastStatus!!.toInt() // 습도는 0~100%
                        ItemTypeRule.SENSOR_TEMPERATURE -> widthPercent = (lastStatus!! + 20).toInt() // 온도는 -20~40
                        ItemTypeRule.READER_RFID -> if (lastStatus != 0.0) widthPercent = 100 // RFID는 탐지 여부로 판정
                        else widthPercent = 0
                        else -> widthPercent = (lastStatus!! / 10).toInt()
                    }
                }
                else -> widthPercent = 0
            }
        }
    }
}