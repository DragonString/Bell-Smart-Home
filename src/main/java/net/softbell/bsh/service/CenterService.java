package net.softbell.bsh.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.softbell.bsh.domain.entity.CenterSetting;
import net.softbell.bsh.domain.repository.CenterSettingRepo;
import net.softbell.bsh.dto.request.CenterSettingDto;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : Center Setting 서비스
 */
@RequiredArgsConstructor
@Service
public class CenterService
{
	// Global Field
	private final CenterSettingRepo centerSettingRepo;
	@Getter
	private CenterSetting setting;
	
	public CenterSetting createSetting(boolean isEnabled)
	{
		// Field
		CenterSetting centerSetting;
		
		// Init
		centerSetting = CenterSetting.builder()
								.isEnabled((byte) 0) // 센터 설정 오버라이드 사용 여부 (true[1]: 활성화, false[0]: 비활성화)
								.iotAction((byte) 1) // IoT 액션 사용 여부 (0: 비활성화, 1: 활성화)
								.iotControl((byte) 1) // IoT 제어 사용 여부 (0: 비활성화, 1: 활성화)
								.iotMonitor((byte) 1) // IoT 모니터 사용 여부 (0: 비활성화, 1: 활성화)
								.iotNode((byte) 1) // IoT 노드 신규 등록 사용 여부 (0: 비활성화, 1: 활성화)
								.iotReserv((byte) 1) // IoT 예약 사용 여부 (0: 비활성화, 1: 활성화)
								.iotTrigger((byte) 1) // IoT 트리거 사용 여부 (0: 비활성화, 1: 활성화)
								.webAuthMode((byte) 0) // 웹 인증 모드(0: 인증 없음, 1: 이메일 인증)
								.webLoginFailBanTime(60 * 5) // 연속 로그인 실패시 5분간 차단
								.webLoginFailCheckTime(60 * 5) // 5분 안에 연속 로그인 실패시
								.webLoginFailMaxCount((byte) 5) // 5회 이상 연속 로그인 실패시
								.webMaintenance((byte) 0) // 웹 유지보수 모드 (0: 비활성화, 1: 활성화)
								.webRegister((byte) 1) // 추가 회원가입 방지 여부 (0: 회원가입 불가능, 1: 회원가입 가능)
									.build();
		
		// Check
		if (isEnabled)
			centerSetting.setIsEnabled((byte) 1);
		
		// Return
		return centerSetting;
	}
	
	@PostConstruct
	@Transactional
	public CenterSetting loadSetting()
	{
		// Field
		List<CenterSetting> listSetting;
		CenterSetting centerSetting;
		
		// Init
		this.setting = null;
		centerSetting = null;
		listSetting = centerSettingRepo.findAll();
		
		// DB - Load
		if (listSetting.isEmpty() || listSetting.size() != 1)
			centerSettingRepo.deleteAll(); // DB - Delete
		else
		{
			centerSetting = listSetting.get(0); // DB에 있는 센터설정값 로드
			if (centerSetting.getIsEnabled() == (byte) 1) // 센터 설정이 사용중이면 그대로 반영
				this.setting = centerSetting;
		}
		
		// Default Value Load
		if (centerSetting == null)
		{
			centerSetting = createSetting(false);
			
			// DB - Save
			centerSettingRepo.save(centerSetting);
		}
		if (this.setting == null)
			this.setting = createSetting(true);
		
		// Return
		return centerSetting;
	}
	
	@Transactional
	public boolean modifyCenterSetting(CenterSettingDto centerSettingDto)
	{
		// Field
		CenterSetting centerSetting;
		
		// Init
		centerSettingRepo.deleteAll();
		
		// Process
		centerSetting = CenterSetting.builder()
											.isEnabled(centerSettingDto.getIsEnabled())
											.iotAction(centerSettingDto.getIotAction())
											.iotControl(centerSettingDto.getIotControl())
											.iotMonitor(centerSettingDto.getIotMonitor())
											.iotNode(centerSettingDto.getIotNode())
											.iotReserv(centerSettingDto.getIotReserv())
											.iotTrigger(centerSettingDto.getIotTrigger())
											.webAuthMode(centerSettingDto.getWebAuthMode())
											.webLoginFailBanTime(centerSettingDto.getWebLoginFailBanTime())
											.webLoginFailCheckTime(centerSettingDto.getWebLoginFailCheckTime())
											.webLoginFailMaxCount(centerSettingDto.getWebLoginFailMaxCount())
											.webMaintenance(centerSettingDto.getWebMaintenance())
											.webRegister(centerSettingDto.getWebRegister())
												.build();
		
		// DB - Save
		centerSettingRepo.save(centerSetting);
		
		// Reload
		loadSetting();
		
		// Return
		return true;
	}
}
