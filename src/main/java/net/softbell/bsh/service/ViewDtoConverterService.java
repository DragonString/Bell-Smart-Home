package net.softbell.bsh.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import net.softbell.bsh.domain.entity.Node;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeActionItem;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeReserv;
import net.softbell.bsh.domain.entity.NodeReservAction;
import net.softbell.bsh.dto.view.ActionItemCardDto;
import net.softbell.bsh.dto.view.ActionSummaryCardDto;
import net.softbell.bsh.dto.view.MonitorSummaryCardDto;
import net.softbell.bsh.dto.view.ReservActionCardDto;
import net.softbell.bsh.dto.view.ReservSummaryCardDto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 엔티티 to 뷰 DTO 변환 서비스
 */
@Service
public class ViewDtoConverterService
{
	// Node Entity List to Monitor Card Dto List
	public List<MonitorSummaryCardDto> convMonitorSummaryCards(Collection<Node> listEntity)
	{
		// Field
		List<MonitorSummaryCardDto> listCards;
		
		// Init
		listCards = new ArrayList<MonitorSummaryCardDto>();
		
		// Process
		for (Node entity : listEntity)
			listCards.add(new MonitorSummaryCardDto(entity));
		
		// Return
		return listCards;
	}

	// NodeReserv Entity List to Reserv Card Dto List
	public List<ReservSummaryCardDto> convReservSummaryCards(Collection<NodeReserv> listEntity)
	{
		// Field
		List<ReservSummaryCardDto> listCards;
		
		// Init
		listCards = new ArrayList<ReservSummaryCardDto>();
		
		// Process
		for (NodeReserv entity : listEntity)
			listCards.add(new ReservSummaryCardDto(entity));
		
		// Return
		return listCards;
	}

	// NodeAction Entity List to Reserv Action Card Dto List
	public <T> List<ReservActionCardDto> convReservActionCards(Collection<T> listEntity)
	{
		// Field
		List<ReservActionCardDto> listCards;
		
		// Init
		listCards = new ArrayList<ReservActionCardDto>();
		
		// Process
		for (T entity : listEntity)
			if (entity instanceof NodeAction)
				listCards.add(new ReservActionCardDto((NodeAction) entity));
			else if (entity instanceof NodeReservAction)
				listCards.add(new ReservActionCardDto(((NodeReservAction) entity).getNodeAction()));
		
		// Return
		return listCards;
	}

	// NodeAction Entity List to Action Card Dto List
	public List<ActionSummaryCardDto> convActionSummaryCards(Collection<NodeAction> listEntity)
	{
		// Field
		List<ActionSummaryCardDto> listCards;
		
		// Init
		listCards = new ArrayList<ActionSummaryCardDto>();
		
		// Process
		for (NodeAction entity : listEntity)
			listCards.add(new ActionSummaryCardDto(entity));
		
		// Return
		return listCards;
	}

	// NodeItem Entity List to Action Item Card Dto List
	public <T> List<ActionItemCardDto> convActionItemCards(Collection<T> listEntity)
	{
		// Field
		List<ActionItemCardDto> listCards;
		
		// Init
		listCards = new ArrayList<ActionItemCardDto>();
		
		// Process
		for (T entity : listEntity)
			if (entity instanceof NodeItem)
				listCards.add(new ActionItemCardDto((NodeItem) entity));
			else if (entity instanceof NodeActionItem)
				listCards.add(new ActionItemCardDto((NodeActionItem) entity));
		
		// Return
		return listCards;
	}
}
