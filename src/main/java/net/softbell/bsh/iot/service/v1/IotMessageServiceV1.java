package net.softbell.bsh.iot.service.v1;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1Dto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Message 서비스
 */
@AllArgsConstructor
@Service
public class IotMessageServiceV1
{
	public BaseV1Dto getBaseMessage(String target, String cmd, String type, String obj, Object value)
	{
		// Field
		BaseV1Dto message;
		
		// Init
		message = BaseV1Dto.builder().sender("SERVER")
									.target(target)
									.cmd(cmd)
									.type(type)
									.obj(obj)
									.value(value)
										.build();
		
		// Return
		return message;
	}
}
