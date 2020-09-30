package net.softbell.bsh.dto.view.general

import net.softbell.bsh.domain.entity.Node
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터뷰 카드정보 DTO
 */
class MonitorSummaryCardDto(entity: Node?) {
    var alias: String?
    var listItems: MutableList<MonitorCardItemDto>
    var lastReceive: Date? = null
    var lastReceiveSecond: Long? = null

    init {
        // Exception
        entity.let {
            // Init
            listItems = java.util.ArrayList()

            // Convert
            alias = entity!!.alias
        }
    }
}