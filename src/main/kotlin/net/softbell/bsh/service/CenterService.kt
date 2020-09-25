package net.softbell.bsh.service

import jdk.nashorn.internal.objects.annotations.Getter
import net.softbell.bsh.domain.entity.CenterSetting
import net.softbell.bsh.domain.repository.CenterSettingRepo
import net.softbell.bsh.dto.request.CenterSettingDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.transaction.Transactional

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : Center Setting 서비스
 */
@Service
class CenterService {
    // Global Field
    @Autowired lateinit var centerSettingRepo: CenterSettingRepo
    private var setting: CenterSetting? = null

    fun createSetting(isEnabled: Boolean): CenterSetting {
        // Field
        val centerSetting: CenterSetting

        // Init
        centerSetting = builder()
                .isEnabled(0.toByte()) // 센터 설정 오버라이드 사용 여부 (true[1]: 활성화, false[0]: 비활성화)
                .iotAction(1.toByte()) // IoT 액션 사용 여부 (0: 비활성화, 1: 활성화)
                .iotControl(1.toByte()) // IoT 제어 사용 여부 (0: 비활성화, 1: 활성화)
                .iotMonitor(1.toByte()) // IoT 모니터 사용 여부 (0: 비활성화, 1: 활성화)
                .iotNode(1.toByte()) // IoT 노드 신규 등록 사용 여부 (0: 비활성화, 1: 활성화)
                .iotReserv(1.toByte()) // IoT 예약 사용 여부 (0: 비활성화, 1: 활성화)
                .iotTrigger(1.toByte()) // IoT 트리거 사용 여부 (0: 비활성화, 1: 활성화)
                .webAuthMode(0.toByte()) // 웹 인증 모드(0: 인증 없음, 1: 이메일 인증)
                .webLoginFailBanTime(60 * 5) // 연속 로그인 실패시 5분간 차단
                .webLoginFailCheckTime(60 * 5) // 5분 안에 연속 로그인 실패시
                .webLoginFailMaxCount(5.toByte()) // 5회 이상 연속 로그인 실패시
                .webMaintenance(0.toByte()) // 웹 유지보수 모드 (0: 비활성화, 1: 활성화)
                .webRegister(1.toByte()) // 추가 회원가입 방지 여부 (0: 회원가입 불가능, 1: 회원가입 가능)
                .build()

        // Check
        if (isEnabled) centerSetting.setIsEnabled(1.toByte())

        // Return
        return centerSetting
    }

    @PostConstruct
    @Transactional
    fun loadSetting(): CenterSetting? {
        // Field
        val listSetting: List<CenterSetting?>
        var centerSetting: CenterSetting?

        // Init
        setting = null
        centerSetting = null
        listSetting = centerSettingRepo!!.findAll()

        // DB - Load
        if (listSetting.isEmpty() || listSetting.size != 1) centerSettingRepo.deleteAll() // DB - Delete
        else {
            centerSetting = listSetting[0] // DB에 있는 센터설정값 로드
            if (centerSetting?.isEnabled === 1 as kotlin.Byte) // 센터 설정이 사용중이면 그대로 반영
                setting = centerSetting
        }

        // Default Value Load
        if (centerSetting == null) {
            centerSetting = createSetting(false)

            // DB - Save
            centerSettingRepo.save(centerSetting)
        }
        if (setting == null) setting = createSetting(true)

        // Return
        return centerSetting
    }

    @Transactional
    fun modifyCenterSetting(centerSettingDto: CenterSettingDto): Boolean {
        // Field
        val centerSetting: CenterSetting

        // Init
        centerSettingRepo!!.deleteAll()

        // Process
        centerSetting = builder()
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
                .build()

        // DB - Save
        centerSettingRepo.save(centerSetting)

        // Reload
        loadSetting()

        // Return
        return true
    }
}