package net.softbell.bsh.iot.service.v1

import lombok.AllArgsConstructor
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto
import org.springframework.stereotype.Service
import kotlin.Throws

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Message 서비스
 */
@AllArgsConstructor
@Service
class IotMessageServiceV1 {
    fun getBaseMessage(target: String?, cmd: String?, type: String?, obj: String?, value: Any?): BaseV1Dto {
        // Field
        val message: BaseV1Dto

        // Init
        message = builder().sender("SERVER")
                .target(target)
                .cmd(cmd)
                .type(type)
                .obj(obj)
                .value(value)
                .build()

        // Return
        return message
    }
}