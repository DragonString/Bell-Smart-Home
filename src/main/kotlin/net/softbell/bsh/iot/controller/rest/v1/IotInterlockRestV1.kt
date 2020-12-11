package net.softbell.bsh.iot.controller.rest.v1

import mu.KLogging
import net.softbell.bsh.dto.response.ResultDto
import net.softbell.bsh.iot.service.v1.IotActionServiceV1
import net.softbell.bsh.service.InterlockService
import net.softbell.bsh.service.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 외부 연동 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/interlock")
class IotInterlockRestV1 {
    // Global Field
    @Autowired private lateinit var responseService: ResponseService
    @Autowired private lateinit var iotActionService: IotActionServiceV1
    @Autowired private lateinit var interlockService: InterlockService

    @PostMapping("/{token}/action/{id}")
    fun execNodeAction(@PathVariable("token") token: String, @PathVariable("id") actionId: Long /*,
									@RequestParam("id")String id,+
									@RequestParam("password")String password*/): ResultDto {
        // Init
        var isSuccess = false
        val member = interlockService.findEnableTokenToMember(token)

        if (member != null)
            isSuccess = iotActionService.execPrivilegesAction(actionId, member)

        // Return
        return if (isSuccess) {
            logger.info("연동 Action 수행 완료 ($actionId)")
            responseService.getSuccessResult()
        } else {
            logger.info("연동 Action 수행 실패 ($actionId)")
            responseService.getFailResult(-10, "해당하는 아이템이 없음")
        }
    }

    @PostMapping("/wol")
    fun execWoL(@RequestParam("mac") macStr: String): ResultDto {
        // Init
        val ipv4 = "255.255.255.255"

        // Process
        return try {
            // Process - Create Packet
            val macBytes = getMacBytes(macStr)
            val bytes = ByteArray(6 + 16 * macBytes.size)
            for (i in 0..5) bytes[i] = 0xff.toByte()
            var i = 6

            while (i < bytes.size) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.size)
                i += macBytes.size
            }

            // Process - Send Magic Packet
            val address = InetAddress.getByName(ipv4)
            val packet = DatagramPacket(bytes, bytes.size, address, PORT)
            val socket = DatagramSocket()

            socket.send(packet)
            socket.close()
            logger.info("WoL Magic Packet Send ($macStr)")
            responseService.getSuccessResult()
        } catch (e: Exception) {
            logger.info("WoL Magic Packet Send Failed ($macStr)")
            responseService.getFailResult(-10, "해당하는 아이템이 없음")
        }
    }

    @Throws(IllegalArgumentException::class)
    private fun getMacBytes(macStr: String): ByteArray {
        // Field
        val bytes = ByteArray(6)
        val hex = macStr.split("(\\:|\\-)").toTypedArray()

        // Exception
        require(hex.size == 6) { "Invalid MAC address." }

        // Process
        try {
            for (i in 0..5) bytes[i] = hex[i].toInt(16).toByte()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Invalid hex digit in MAC address.")
        }

        // Return
        return bytes
    }

    companion object : KLogging() {
        const val PORT = 9
    }
}