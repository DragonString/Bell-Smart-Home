package net.softbell.bsh.dto.view.general

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 모니터뷰 카드정보 DTO
 */
class MonitorSummaryCardDto(entity: Node?) {
    private val alias: String
    private val listItems: List<MonitorCardItemDto>
    private val lastReceive: Date? = null
    private val lastReceiveSecond: Long? = null

    init {
        // Exception
        if (entity == null) return

        // Init
        listItems = ArrayList()

        // Convert
        alias = entity.getAlias()
    }
}