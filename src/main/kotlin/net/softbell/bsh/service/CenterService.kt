package net.softbell.bsh.service

import net.softbell.bsh.domain.entity.CenterSetting
import net.softbell.bsh.domain.repository.CenterSettingRepo
import net.softbell.bsh.dto.request.CenterSettingDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct
import javax.transaction.Transactional

/**
 * @author : Bell(bell@softbell.net)
 * @description : Center Setting 서비스
 */
@Service
class CenterService {
    // Global Field
    @Autowired private lateinit var centerSettingRepo: CenterSettingRepo
    lateinit var setting: CenterSetting // 현재 운영 값

    /**
     * 센터 설정 기본값 생성
     */
    fun createSetting(isEnabled: Boolean): CenterSetting {
        // Default Init
        val centerSetting = CenterSetting(
                isEnabled = 0.toByte(), // 센터 설정 오버라이드 사용 여부 (true[1]: 활성화, false[0]: 비활성화)
                iotAction = 1.toByte(), // IoT 액션 사용 여부 (0: 비활성화, 1: 활성화)
                iotControl = 1.toByte(), // IoT 제어 사용 여부 (0: 비활성화, 1: 활성화)
                iotMonitor = 1.toByte(), // IoT 모니터 사용 여부 (0: 비활성화, 1: 활성화)
                iotNode = 1.toByte(), // IoT 노드 신규 등록 사용 여부 (0: 비활성화, 1: 활성화)
                iotReserv = 1.toByte(), // IoT 예약 사용 여부 (0: 비활성화, 1: 활성화)
                iotTrigger = 1.toByte(), // IoT 트리거 사용 여부 (0: 비활성화, 1: 활성화)
                webAuthMode = 0.toByte(), // 웹 인증 모드(0: 인증 없음, 1: 이메일 인증)
                webLoginFailBanTime = 60 * 5, // 연속 로그인 실패시 5분간 차단
                webLoginFailCheckTime = 60 * 5, // 5분 안에 연속 로그인 실패시
                webLoginFailMaxCount = 5.toByte(), // 5회 이상 연속 로그인 실패시
                webMaintenance = 0.toByte(), // 웹 유지보수 모드 (0: 비활성화, 1: 활성화)
                webRegister = 1.toByte() // 추가 회원가입 방지 여부 (0: 회원가입 불가능, 1: 회원가입 가능)
        )

        // Check
        if (isEnabled) centerSetting.isEnabled = 1.toByte()

        // Return
        return centerSetting
    }

    /**
     * 현재 설정 값 반환
     */
    @PostConstruct
    @Transactional
    fun loadSetting(): CenterSetting {
        // Init
        var centerSetting: CenterSetting? = null
        val listSetting: List<CenterSetting> = centerSettingRepo.findAll()

        // DB - Load
        if (listSetting.size != 1) // DB에 세팅값이 1개 초과면 (이상 저장)
            centerSettingRepo.deleteAll() // DB - Delete

        if (listSetting.isEmpty()) { // DB에 세팅값이 없으면
            setting = createSetting(true) // 신규 세팅 등록
            centerSettingRepo.save(setting) // DB에 등록
        }
        else { // DB에 세팅값이 있으면
            centerSetting = listSetting[0] // DB에 있는 센터설정값 로드

            setting = if (centerSetting.isEnabled == 1.toByte()) // 센터 설정이 사용중이면 그대로 반영
                centerSetting
            else // 센터 설정이 미사용중
                createSetting(true) // 기본값으로 오버라이드 비활성화로 생성
        }

        // Return
        if (centerSetting != null) // DB에 센터 설정이 없으면
            return centerSetting
        return setting
    }

    @Transactional
    fun modifyCenterSetting(centerSettingDto: CenterSettingDto): Boolean {
        // Init
        val listSetting: List<CenterSetting?> = centerSettingRepo.findAll()

        // Process
        if (listSetting.isNotEmpty())
            listSetting[0]?.let {
                it.isEnabled = centerSettingDto.isEnabled
                it.iotAction = centerSettingDto.iotAction
                it.iotControl = centerSettingDto.iotControl
                it.iotMonitor = centerSettingDto.iotMonitor
                it.iotNode = centerSettingDto.iotNode
                it.iotReserv = centerSettingDto.iotReserv
                it.iotTrigger = centerSettingDto.iotTrigger
                it.webAuthMode = centerSettingDto.webAuthMode
                it.webLoginFailBanTime = centerSettingDto.webLoginFailBanTime
                it.webLoginFailCheckTime = centerSettingDto.webLoginFailCheckTime
                it.webLoginFailMaxCount = centerSettingDto.webLoginFailMaxCount
                it.webMaintenance = centerSettingDto.webMaintenance
                it.webRegister = centerSettingDto.webRegister

                centerSettingRepo.save(it)
            }

        // Reload
        loadSetting()

        // Return
        return true
    }
}