package net.softbell.bsh.service

import mu.KLogging
import net.softbell.bsh.domain.ItemTypeRule
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo
import net.softbell.bsh.domain.repository.NodeItemRepo
import net.softbell.bsh.dto.view.DashboardAvgCardDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 응답 메시지 처리 서비스
 */
@Service
class DashboardService {
    // Global Field
    @Autowired private lateinit var nodeItemRepo: NodeItemRepo
    @Autowired private lateinit var nodeItemHistoryRepo: NodeItemHistoryRepo


    fun getHumidityWarn(): List<DashboardAvgCardDto>? {
        // Field
        val listHumidityCardDto: MutableList<DashboardAvgCardDto>
        val listNodeItem: List<NodeItem?>?

        // Init
        listHumidityCardDto = ArrayList()
        listNodeItem = nodeItemRepo.findByItemType(ItemTypeRule.SENSOR_HUMIDITY)

        // Exception
        if (listNodeItem!!.size <= 0) return null

        // Process
        for (nodeItem in listNodeItem) {
            // Field
            var avgStatus: Double?
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, -5) // 5분 평균값 기준

            // Init
            val beforeTime = System.currentTimeMillis()
            avgStatus = nodeItemHistoryRepo.avgByNodeItem(nodeItem, calendar.time)
            val afterTime = System.currentTimeMillis() // 코드 실행 후에 시간 받아오기
            val secDiffTime = afterTime - beforeTime //두 시간에 차 계산
            logger.info(nodeItem?.alias + "평균 로드 끝 (" + secDiffTime + "ms)")

            // Process
            if (avgStatus != null && !(avgStatus > 40 && avgStatus <= 60)) {
                var dashboardAvgCardDto = DashboardAvgCardDto()
                dashboardAvgCardDto.alias = nodeItem?.alias
                dashboardAvgCardDto.avgStatus = avgStatus
                listHumidityCardDto.add(dashboardAvgCardDto)
            }
        }

        // Return
        return listHumidityCardDto
    }

    fun getTemperatureWarn(): List<DashboardAvgCardDto>? {
        // Field
        val listHumidityCardDto: MutableList<DashboardAvgCardDto>
        val listNodeItem: List<NodeItem?>?

        // Init
        listHumidityCardDto = ArrayList()
        listNodeItem = nodeItemRepo.findByItemType(ItemTypeRule.SENSOR_TEMPERATURE)

        // Exception
        if (listNodeItem!!.size <= 0) return null

        // Process
        for (nodeItem in listNodeItem) {
            // Field
            var avgStatus: Double?
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MINUTE, -5) // 5분 평균값 기준

            // Init
            val beforeTime = System.currentTimeMillis()
            avgStatus = nodeItemHistoryRepo.avgByNodeItem(nodeItem, calendar.time)
            val afterTime = System.currentTimeMillis() // 코드 실행 후에 시간 받아오기
            val secDiffTime = afterTime - beforeTime //두 시간에 차 계산
            logger.info(nodeItem?.alias + "평균 로드 끝 (" + secDiffTime + "ms)")

            // Process
            if (avgStatus != null && !(avgStatus >= 18 && avgStatus <= 22)) {
                var dashboardAvgCardDto = DashboardAvgCardDto()
                dashboardAvgCardDto.alias = nodeItem?.alias
                dashboardAvgCardDto.avgStatus = avgStatus
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