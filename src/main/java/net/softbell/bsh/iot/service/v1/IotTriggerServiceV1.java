package net.softbell.bsh.iot.service.v1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeRepo;
import net.softbell.bsh.iot.component.v1.IotComponentV1;
import net.softbell.bsh.iot.dto.bshp.v1.ItemInfoV1DTO;
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1DTO;
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1DTO;
import net.softbell.bsh.libs.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Trigger 서비스
 */
@Service
public class IotTriggerServiceV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IotComponentV1 iotComponentV1;
	
	
}
