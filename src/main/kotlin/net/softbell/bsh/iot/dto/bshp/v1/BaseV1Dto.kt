package net.softbell.bsh.iot.dto.bshp.v1

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : Bell Smart Home Protocol v1 DTO
 */
data class BaseV1Dto (
        val sender: String,
        val target: String,
        val cmd: String,
        val type: String,
        val obj: String,
        val value: Any?
)