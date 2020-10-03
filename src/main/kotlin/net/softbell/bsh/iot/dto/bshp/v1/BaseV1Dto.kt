package net.softbell.bsh.iot.dto.bshp.v1

/**
 * @author : Bell(bell@softbell.net)
 * @description : Bell Smart Home Protocol v1 DTO
 */
data class BaseV1Dto (
    var sender: String,
    var target: String,
    var cmd: String,
    var type: String,
    var obj: String,
    var value: Any?
)