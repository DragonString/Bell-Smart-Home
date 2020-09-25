package net.softbell.bsh.dto.view.general

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.NodeReserv
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 등록 및 수정뷰 정보 카드정보 DTO
 */
@Getter
@Setter
class ReservInfoCardDto(entity: NodeReserv?) {
    private val reservId: Long
    private var enableStatus: Boolean
    private val description: String
    private val expression: String

    init {
        // Exception
        if (entity == null) return

        // Init
        enableStatus = false

        // Convert
        reservId = entity.getReservId()
        if (entity.getEnableStatus() === EnableStatusRule.ENABLE) enableStatus = true
        description = entity.getDescription()
        expression = entity.getExpression()
    }
}