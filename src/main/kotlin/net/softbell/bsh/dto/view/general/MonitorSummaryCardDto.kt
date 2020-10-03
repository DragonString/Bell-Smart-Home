package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.Node
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 모니터뷰 카드정보 DTO
 */
class MonitorSummaryCardDto(entity: Node) {
    val alias: String = entity.alias
    val listItems: MutableList<MonitorCardItemDto> = ArrayList()
    var lastReceive: Date? = null
    var lastReceiveSecond: Long? = null
}