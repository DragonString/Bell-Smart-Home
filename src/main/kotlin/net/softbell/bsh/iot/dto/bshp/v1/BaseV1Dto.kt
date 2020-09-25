package net.softbell.bsh.iot.dto.bshp.v1

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.Setter
import lombok.ToString
import javax.validation.constraints.Null
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : Bell Smart Home Protocol v1 DTO
 */
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
class BaseV1Dto {
    @NonNull
    private val sender: String? = null

    @NonNull
    private val target: String? = null

    @NonNull
    private val cmd: String? = null

    @NonNull
    private val type: String? = null

    @NonNull
    private val obj: String? = null

    @Null
    private val value: Any? = null
}