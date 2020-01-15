package net.softbell.bsh.iot.service.v1;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeRepo;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Node Action 서비스
 */
@Slf4j
@AllArgsConstructor
@Service
public class IotMonitorServiceV1
{
	// Global Field
	private final NodeRepo nodeRepo;
	private final NodeItemRepo nodeItemRepo;
	private final NodeItemHistoryRepo nodeItemHistoryRepo;
	
	public Page<Node> getAllNodes(int intPage, int intCount)
	{
		// Field
		Pageable curPage;
		
		// Init
		curPage = PageRequest.of(intPage - 1, intCount);
		
		// Return
		return nodeRepo.findAll(curPage);
	}
	
	public Node getNode(long nodeId)
	{
		// Field
		Optional<Node> optNode;
		
		// Init
		optNode = nodeRepo.findById(nodeId);
		
		// Return
		if (optNode.isPresent())
			return optNode.get();
		else
			return null;
	}
	
	public List<NodeItemHistory> getNodeItemHistory(long nodeItemId)
	{
		// Field
		List<NodeItemHistory> listNodeItemHistory;
		Pageable curPage;
		Optional<NodeItem> optNodeItem;
		
		// Init
		optNodeItem = nodeItemRepo.findById(nodeItemId);
		
		// Exception
		if (!optNodeItem.isPresent())
			return null;
		
		// Process
		curPage = PageRequest.of(0, 50, new Sort(Direction.DESC, "itemHistoryId")); // TODO 임시로 50개 뽑게 설정함
		listNodeItemHistory = nodeItemHistoryRepo.findByNodeItem(optNodeItem.get(), curPage);
		Collections.reverse(listNodeItemHistory);
		
		// Return
		return listNodeItemHistory;
	}
}
