package net.softbell.bsh.iot.service.v1;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.domain.entity.CenterSetting;
import net.softbell.bsh.domain.repository.CenterSettingRepo;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT Center Setting 서비스
 */
@RequiredArgsConstructor
@Service
public class IotCenterServiceV1
{
	// Global Field
	private final CenterSettingRepo centerSettingRepo;
	private CenterSetting setting;
	
	public CenterSetting getSetting()
	{
		// Field
		Optional<CenterSetting> optSetting;
		
		// Init
		optSetting = centerSettingRepo.findById((byte) 1);
		
		// Exception
		if (optSetting.isPresent())
		{
			setting = optSetting.get(); // DB에 있는 센터설정값 로드
			if (setting.getIsEnabled() == (byte) 1) // 센터 설정이 사용중이면 그대로 반영
				return setting;
		}
		
		// Default Value Load
		setting = CenterSetting.builder()
								.isEnabled((byte) 1) // 센터 설정 오버라이드 사용 여부 (true[1]: 활성화, false[0]: 비활성화)
								.iotAction((byte) 0) // IoT 액션 사용 여부 (0: 활성화, 1: 비활성화)
								.iotControl((byte) 0) // IoT 제어 사용 여부 (0: 활성화, 1: 비활성화)
								.iotMonitor((byte) 0) // IoT 모니터 사용 여부 (0: 활성화, 1: 비활성화)
								.iotNode((byte) 0) // IoT 노드 신규 등록 사용 여부 (0: 활성화, 1: 비활성화)
								.iotReserv((byte) 0) // IoT 예약 사용 여부 (0: 활성화, 1: 비활성화)
								.iotTrigger((byte) 0) // IoT 트리거 사용 여부 (0: 활성화, 1: 비활성화)
								.webAuthMode((byte) 0) // 웹 인증 모드(0: 인증 없음, 1: 이메일 인증)
								.webLoginFailBanTime(60 * 10) // 연속 로그인 실패시 10분간 차단
								.webLoginFailCheckTime(60 * 10) // 10분 안에 연속 로그인 실패시
								.webLoginFailMaxCount((byte) 5) // 5회 이상 연속 로그인 실패시
								.webMaintenance((byte) 0) // 웹 유지보수 모드 (0: 비활성화, 1: 활성화)
								.webRegister((byte) 0) // 추가 회원가입 방지 여부 (0: 회원가입 가능, 1: 불가능)
									.build();
		
		// Return
		return centerSettingRepo.save(setting);
	}
	
	public void saveSetting(CenterSetting setting)
	{
		if (setting != null)
		{
			centerSettingRepo.delete(this.setting);
			this.setting = centerSettingRepo.save(setting);
		}
	}
}
