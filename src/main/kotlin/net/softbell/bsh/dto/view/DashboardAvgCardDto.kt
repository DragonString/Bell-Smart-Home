package net.softbell.bsh.dto.view

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.Setter
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 대시보드 습도 정보 카드정보 DTO
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
class DashboardAvgCardDto {
    private val alias: String? = null
    private val avgStatus: Double? = null
}