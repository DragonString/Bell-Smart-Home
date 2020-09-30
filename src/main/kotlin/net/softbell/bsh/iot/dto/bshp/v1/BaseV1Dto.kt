package net.softbell.bsh.iot.dto.bshp.v1

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : Bell Smart Home Protocol v1 DTO
 */
data class BaseV1Dto (
    var sender: String,
    var target: String,
    var cmd: String,
    var type: String,
    var obj: String,
    var value: Any?
)