package net.softbell.bsh.controller.view;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeReserv;
import net.softbell.bsh.domain.entity.NodeReservAction;
import net.softbell.bsh.dto.request.IotReservDto;
import net.softbell.bsh.dto.view.ReservInfoCardDto;
import net.softbell.bsh.iot.service.v1.IotReservServiceV1;
import net.softbell.bsh.service.ViewDtoConverterService;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 예약 뷰 컨트롤러
 */
@AllArgsConstructor
@Controller
@RequestMapping("/reserv")
public class ReservView
{
	// Global Field
	private final String G_BASE_PATH = "services/general";
	private final ViewDtoConverterService viewDtoConverterService;
	private final IotReservServiceV1 iotReservService;
	
	@GetMapping()
    public String dispIndex(Model model, Authentication auth)
	{
		// Field
		List<NodeReserv> listReserv;
		
		// Init
		listReserv = iotReservService.getAllReservs(auth);
		
		// Process
//		model.addAttribute("listReserv", listReserv);
		model.addAttribute("listCard", viewDtoConverterService.convReservSummaryCards(listReserv));
		
		// Return
        return G_BASE_PATH + "/Reserv";
    }
	
	@GetMapping("/{id}")
    public String dispReserv(Model model, Authentication auth, @PathVariable("id") long reservId)
	{
		// Field
		NodeReserv nodeReserv;
		List<NodeAction> listNodeAction;
		
		// Init
		nodeReserv = iotReservService.getReserv(auth, reservId);
		listNodeAction = iotReservService.getAvailableAction(auth);
		
		for (NodeReservAction reservActionItem : nodeReserv.getNodeReservActions())
			listNodeAction.remove(reservActionItem.getNodeAction());
		
		// Process
		model.addAttribute("cardReservInfo", new ReservInfoCardDto(nodeReserv));
		model.addAttribute("listCardActionAct", viewDtoConverterService.convReservActionCards(nodeReserv.getNodeReservActions()));
		model.addAttribute("listCardAction", viewDtoConverterService.convReservActionCards(listNodeAction));
		
		// Return
        return G_BASE_PATH + "/ReservModify";
    }
	
	@GetMapping("/create")
    public String dispCreate(Model model, Authentication auth)
	{
		// Field
		List<NodeAction> listNodeAction;
		
		// Init
		listNodeAction = iotReservService.getAvailableAction(auth);
		
		// Process
		model.addAttribute("listCardAction", viewDtoConverterService.convReservActionCards(listNodeAction));
		
		// Return
        return G_BASE_PATH + "/ReservCreate";
    }
	
	@PostMapping("/create")
    public String procCreate(Model model, Authentication auth,
    						IotReservDto iotReservationDto)
	{
		// Field
		boolean isSuccess;
		
		// Init
		isSuccess = iotReservService.createReservation(auth, iotReservationDto);
		
		// Return
		if (isSuccess)
			return "redirect:/reserv";
		else
			return "redirect:/reserv?error";
    }
}
