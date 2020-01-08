package net.softbell.bsh.iot.service.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.iot.component.v1.IotComponentV1;
import net.softbell.bsh.iot.dto.bshp.v1.BaseV1DTO;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT UID 서비스
 */
@Service
public class IotUIDServiceV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IotComponentV1 iotComponentV1;
	@Autowired
	private NodeRepo nodeRepo;


	public BaseV1DTO generateToken(String uid)
	{
		// Field
		BaseV1DTO message;
		String token;
		
		// Init
		token = iotComponentV1.generateToken(uid);
		
		// Process
		message = BaseV1DTO.builder().sender("SERVER")
									.target(uid)
									.cmd("SET")
									.type("INFO")
									.obj("TOKEN")
									.value(token)
									.build();
		
		// Return
		return message;
	}
}
