package net.softbell.bsh.dto.view.general

import lombok.Getter
import lombok.Setter
import net.softbell.bsh.domain.entity.NodeAction
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션뷰 카드정보 DTO
 */
@Getter
@Setter
class ActionSummaryCardDto(entity: NodeAction?) {
    private val actionId: Long
    private val description: String
    private val creatorNickname: String

    init {
        // Exception
        if (entity == null) return

        // Convert
        actionId = entity.getActionId()
        description = entity.getDescription()
        creatorNickname = entity.getMember().getNickname()
    }
}