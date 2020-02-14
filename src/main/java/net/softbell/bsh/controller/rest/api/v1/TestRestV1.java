package net.softbell.bsh.controller.rest.api.v1;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 테스트용 REST API 컨트롤러 V1
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rest/v1/test")
public class TestRestV1
{
	// Global Field
	private final NodeItemRepo nodeItemRepo;
    private final NodeItemHistoryRepo nodeItemHistoryRepo;
    
    @GetMapping("/avg")
    public String findAvg(@RequestParam(value = "id", defaultValue = "10", required = false) long nodeItemId, @RequestParam(value = "count", defaultValue = "2", required = false) int count)
    {
    	Optional<NodeItem> optNodeItem = nodeItemRepo.findById(nodeItemId);
    	if (!optNodeItem.isPresent())
    		return "errror";
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.HOUR_OF_DAY, -1);
    	
    	Double result = nodeItemHistoryRepo.minByNodeItem(optNodeItem.get(), calendar.getTime());
    	
    	return result.toString();
    }
}

