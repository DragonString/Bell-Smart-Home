package net.softbell.bsh.controller.rest.api.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.repository.MemberRepo;
import net.softbell.bsh.dto.test.ApiTestDto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 테스트 REST API 컨트롤러 V1
 */
@RestController
@RequestMapping("/api/rest/v1/test")
public class TestRestV1
{
	@Autowired
	MemberRepo memberRepo;
	
	@GetMapping("/")
	public ApiTestDto procTest(@RequestParam(value = "strName", required = false, defaultValue = "이름")String strName,
			@RequestParam(value = "intAge", required = false, defaultValue = "24")Integer intAge)
	{
		// Field
		ApiTestDto apiTest = new ApiTestDto(strName, intAge);
		
		// Finish
		return apiTest;
	}
	
	@GetMapping("/users")
	public List<Member> dispUsers()
	{
		List<Member> listMember = memberRepo.findAll(); 
		
		// Finish
		return listMember;
	}
}
