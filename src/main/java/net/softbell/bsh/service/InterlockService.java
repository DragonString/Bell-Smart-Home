package net.softbell.bsh.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.Member;
import net.softbell.bsh.domain.entity.MemberInterlockToken;
import net.softbell.bsh.domain.repository.MemberInterlockTokenRepo;
import net.softbell.bsh.dto.request.InterlockTokenDto;
import net.softbell.bsh.iot.component.v1.IotAuthCompV1;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 연동 서비스
 */
@AllArgsConstructor
@Service
public class InterlockService
{
	// Global Field
	private final MemberService memberService;
	
	private final IotAuthCompV1 authComp;
	
	private final MemberInterlockTokenRepo memberInterlockTokenRepo;
	
	public List<MemberInterlockToken> getAllTokens(Authentication auth)
	{
		// Field
		Member member;
		
		// Init
		member = memberService.getMember(auth.getName());
		
		// Return
		return memberInterlockTokenRepo.findByMember(member);
	}
	
	public Member findEnableTokenToMember(String token)
	{
		// Field
		MemberInterlockToken memberInterlockToken;
		
		// Init
		memberInterlockToken = memberInterlockTokenRepo.findByEnableStatusAndToken(EnableStatusRule.ENABLE, token);
		
		// Exception
		if (memberInterlockToken == null)
			return null;
		
		// Return
		return memberInterlockToken.getMember();
	}
	
	public Member findTokenToMember(String token)
	{
		// Field
		MemberInterlockToken memberInterlockToken;
		
		// Init
		memberInterlockToken = memberInterlockTokenRepo.findByToken(token);
		
		// Return
		return memberInterlockToken.getMember();
	}
	
	@Transactional
	public boolean createToken(Authentication auth, InterlockTokenDto interlockTokenDto)
	{
		// Field
		Member member;
		MemberInterlockToken memberInterlockToken;
		
		// Init
		member = memberService.getMember(auth.getName());
		
		// Exception
		if (member == null)
			return false;
		
		// Process
		memberInterlockToken = MemberInterlockToken.builder()
								.name(interlockTokenDto.getName())
								.member(member)
								.registerDate(new Date())
								.enableStatus(EnableStatusRule.ENABLE)
								.token(authComp.getRandomToken())
									.build();
		
		// DB - Save
		memberInterlockTokenRepo.save(memberInterlockToken);
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean modifyToken(Authentication auth, long tokenId, EnableStatusRule enableStatus)
	{
		// Field
		Member member;
		Optional<MemberInterlockToken> optMemberInterlockToken;
		
		// Init
		member = memberService.getMember(auth.getName());
		optMemberInterlockToken = memberInterlockTokenRepo.findById(tokenId);
		
		// Exception
		if (!optMemberInterlockToken.isPresent() || member == null)
			return false;
		
		// DB - Update
		optMemberInterlockToken.get().setEnableStatus(enableStatus);
		
		// Return
		return true;
	}
	
	@Transactional
	public boolean deleteToken(Authentication auth, long tokenId)
	{
		// Field
		Member member;
		Optional<MemberInterlockToken> optMemberInterlockToken;
		
		// Init
		member = memberService.getMember(auth.getName());
		optMemberInterlockToken = memberInterlockTokenRepo.findById(tokenId);
		
		// Exception
		if (!optMemberInterlockToken.isPresent() || member == null)
			return false;
		
		// DB - Update
		memberInterlockTokenRepo.delete(optMemberInterlockToken.get());
		
		// Return
		return true;
	}
}
