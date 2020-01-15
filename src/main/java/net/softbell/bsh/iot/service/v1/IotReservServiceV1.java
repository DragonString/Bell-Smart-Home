package net.softbell.bsh.iot.service.v1;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Reservation 서비스
 */
@AllArgsConstructor
@Service
public class IotReservServiceV1
{
	// Global Field
	private final IotChannelCompV1 iotChannelCompV1;
	private final IotAuthCompV1 iotAuthCompV1;


}
