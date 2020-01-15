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
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;
import net.softbell.bsh.iot.dto.bshp.v1.ItemInfoV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.ItemValueV1Dto;
import net.softbell.bsh.iot.dto.bshp.v1.NodeInfoV1Dto;
import net.softbell.bsh.util.BellLog;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Action 서비스
 */
@Service
public class IotActionServiceV1
{
	// Global Field
	private final Logger G_Logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IotChannelCompV1 iotChannelCompV1;
	@Autowired
	private IotAuthCompV1 iotAuthCompV1;
	
	
}
