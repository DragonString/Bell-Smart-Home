package net.softbell.bsh.iot.service.v1;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;
import net.softbell.bsh.iot.component.v1.IotChannelCompV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Action 서비스
 */
@AllArgsConstructor
@Service
public class IotActionServiceV1
{
	// Global Field
	private final IotChannelCompV1 iotChannelCompV1;
	private final IotAuthCompV1 iotAuthCompV1;
	private final NodeItemRepo nodeItemRepo;
	
	public List<NodeItem> getAvailableNodeItem(Authentication auth)
	{
		// Field
		List<NodeItem> listNodeItem;
		
		// TODO 계정에서 접근가능한 아이템만 반환하도록 추가해야됨.. 나중에... 언젠가는 추가하겠지...?
		// Init
		listNodeItem = nodeItemRepo.findAll();
		
		// Return
		return listNodeItem;
	}
}
