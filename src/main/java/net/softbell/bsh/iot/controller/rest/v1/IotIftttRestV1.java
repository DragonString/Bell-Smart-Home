package net.softbell.bsh.iot.controller.rest.v1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.dto.response.ResultDto;
import net.softbell.bsh.iot.service.v1.IotActionServiceV1;
import net.softbell.bsh.service.ResponseService;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT IFTTT REST API 컨트롤러 V1
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/ifttt")
public class IotIftttRestV1
{
	// Global Field
    private final ResponseService responseService;
    private final IotActionServiceV1 iotActionService;
	public static final int PORT = 9;
	
	@PostMapping("/action/{id}")
	public ResultDto execNodeAction(@PathVariable("id")long actionId/*,
									@RequestParam("id")String id,+
									@RequestParam("password")String password*/)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = iotActionService.execAction(actionId);
		
		// Log
		log.info("IFTTT Action 요청됨 (" + actionId + ")");
		
		// Return
		if (isSuccess)
			return responseService.getSuccessResult();
		else
			return responseService.getFailResult(-10, "해당하는 아이템이 없음");
	}
	
	@PostMapping("/wol")
	public ResultDto execWoL(@RequestParam("mac") String macStr)
	{
		// Field
		String ipStr;
		
		// Init
		ipStr = "255.255.255.255";
        
		// Process
        try
        {
        	// Process - Create Packet
            byte[] macBytes = getMacBytes(macStr);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            
            for (int i = 0; i < 6; i++)
                bytes[i] = (byte) 0xff;
            for (int i = 6; i < bytes.length; i += macBytes.length)
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            
            // Process - Send Magic Packet
            InetAddress address = InetAddress.getByName(ipStr);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
            
            log.info(BellLog.getLogHead() + "WoL Magic Packet Send (" + macStr + ")");
            
            return responseService.getSuccessResult();
        }
        catch (Exception e)
        {
            log.info(BellLog.getLogHead() + "WoL Magic Packet Send Failed (" + macStr + ")");
            
            return responseService.getFailResult(-10, "해당하는 아이템이 없음");
        }
	}
    
    private byte[] getMacBytes(String macStr) throws IllegalArgumentException
    {
    	// Field
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("(\\:|\\-)");
        
        // Exception
        if (hex.length != 6)
            throw new IllegalArgumentException("Invalid MAC address.");
        
        // Process
        try
        {
            for (int i = 0; i < 6; i++)
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        
        // Return
        return bytes;
    }
}
