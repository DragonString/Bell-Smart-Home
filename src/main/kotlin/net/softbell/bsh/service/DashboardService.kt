package net.softbell.bsh.service

import mu.KLogging
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo
import net.softbell.bsh.domain.repository.NodeItemRepo
import net.softbell.bsh.dto.view.DashboardAvgCardDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : 응답 메시지 처리 서비스
 */
@Service
class DashboardService {
    // Global Field
    @Autowired private lateinit var nodeItemRepo: NodeItemRepo
    @Autowired private lateinit var nodeItemHistoryRepo: NodeItemHistoryRepo


    fun getHumidityWarn(): List<DashboardAvgCardDto> {
        // Init
        val listHumidityCardDto: MutableList<DashboardAvgCardDto> = ArrayList()
        val listNodeItem = nodeItemRepo.findByItemType(ItemTypeRule.SENSOR_HUMIDITY)

        // Exception
        if (listNodeItem.isEmpty())
            return emptyList()

        // Process
        for (nodeItem in listNodeItem) {
            // Field
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, -5) // 5분 평균값 기준

            // Init
            val beforeTime = System.currentTimeMillis()
            val avgStatus = nodeItemHistoryRepo.avgByNodeItem(nodeItem, calendar.time) ?: continue
            val afterTime = System.currentTimeMillis() // 코드 실행 후에 시간 받아오기
            val secDiffTime = afterTime - beforeTime //두 시간에 차 계산
            val alias = nodeItem.node.alias

            logger.info("$alias 평균 습도($avgStatus %) 로드 끝 (${secDiffTime}ms)") // TODO Tuning log

            // Process
            if (avgStatus !in 40.0..60.0) { // 평균이 40 이상 60 이하가 아니면 경고 카드 생성
                val dashboardAvgCardDto = DashboardAvgCardDto(
                        alias = alias,
                        avgStatus = avgStatus
                )
                listHumidityCardDto.add(dashboardAvgCardDto)
            }
        }

        // Return
        return listHumidityCardDto
    }

    fun getTemperatureWarn(): List<DashboardAvgCardDto> {
        // Init
        val listHumidityCardDto: MutableList<DashboardAvgCardDto> = ArrayList()
        val listNodeItem = nodeItemRepo.findByItemType(ItemTypeRule.SENSOR_TEMPERATURE)

        // Exception
        if (listNodeItem.isEmpty())
            return emptyList()

        // Process
        for (nodeItem in listNodeItem) {
            // Field
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, -5) // 5분 평균값 기준

            // Init
            val beforeTime = System.currentTimeMillis()
            var avgStatus = nodeItemHistoryRepo.avgByNodeItem(nodeItem, calendar.time) ?: continue
            val afterTime = System.currentTimeMillis() // 코드 실행 후에 시간 받아오기
            val secDiffTime = afterTime - beforeTime //두 시간에 차 계산
            val alias = nodeItem.node.alias

            logger.info("$alias 평균 온도 ($avgStatus C) 로드 끝 (${secDiffTime}ms)") // TODO Tuning log

            // Process
            if (avgStatus !in 18.0..22.0) { // 평균이 18 이상 22 이하가 아닐 경우 경고 카드 생성
                var dashboardAvgCardDto = DashboardAvgCardDto(
                        alias = alias,
                        avgStatus = avgStatus
                )
                listHumidityCardDto.add(dashboardAvgCardDto)
            }
        }

        // Return
        return listHumidityCardDto
    }

    /*public DashboardAvgCardDto getHumidityAvg()
    {
        // Field
        DashboardAvgCardDto humidityCardDto;
        List<NodeItem> listNodeItem;
        Double totalAvgStatus;

        // Init
        listNodeItem = nodeItemRepo.findByItemType(ItemTypeRule.SENSOR_HUMIDITY);

        // Exception
        if (listNodeItem.size() <= 0)
            return null;

        // Process
        for (NodeItem nodeItem : listNodeItem)
        {
            // Field
            Double avgStatus;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, -5); // 5분 평균값 기준

            // Init
            long beforeTime = System.currentTimeMillis();
            avgStatus = nodeItemHistoryRepo.avgByNodeItem(nodeItem, calendar.getTime());
            long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
            long secDiffTime = (afterTime - beforeTime); //두 시간에 차 계산
            logger.info(nodeItem.getAlias() + "평균 로드 끝 (" + secDiffTime + "ms)");

            // Process
            if (avgStatus != null && !(avgStatus > 40 && avgStatus <= 60))
                listHumidityCardDto.add(DashboardAvgCardDto.builder().alias(nodeItem.getNode().getAlias()).avgStatus(avgStatus).build());
        }

        // Return
        return listHumidityCardDto;
    }*/

    companion object : KLogging()
}