package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.ItemModeRule
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory

/**
 * @author : Bell(bell@softbell.net)
 * @description : 모니터뷰 카드 아이템 정보 DTO
 */
class MonitorCardItemDto(entity: NodeItem, lastHistory: NodeItemHistory) {
    val alias: String = entity.alias
    val lastStatus: Double = lastHistory.itemStatus
    val widthPercent: Int
    val digital: Boolean

    init {
        // Convert
        when (entity.itemMode) {
            ItemModeRule.DIGITAL -> {
                digital = true
                widthPercent = if (lastStatus == 0.0)
                    0
                else
                    100
            }
            ItemModeRule.ANALOG -> {
                digital = false
                widthPercent = when (entity.itemType) {
                    ItemTypeRule.SENSOR_HUMIDITY -> lastStatus.toInt() // 습도는 0~100%
                    ItemTypeRule.SENSOR_TEMPERATURE -> (lastStatus + 20).toInt() // 온도는 -20~40
                    ItemTypeRule.READER_RFID -> if (lastStatus != 0.0)
                        100 // RFID는 탐지 여부로 판정
                    else
                        0
                    else -> (lastStatus / 10).toInt()
                }
            }
            else -> {
                digital = false
                widthPercent = 0
            }
        }
    }
}