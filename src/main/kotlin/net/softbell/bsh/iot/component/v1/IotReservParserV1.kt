package net.softbell.bsh.iot.component.v1

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeReserv
import net.softbell.bsh.domain.repository.NodeReservRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

/**
 * @author : Bell(bell@softbell.net)
 * @description : IoT 예약 표현식 파서 v1
 * 리눅스의 cron 표현식과 동일하게 사용
 * 분(0-59) 시간(0-23) 일(1-13) 월(1-12) 요일(0-7)
 *
 * 각자 위치에 사용 가능한 표현
 * *: 항상
 * 1: 1에 해당될 때
 * 1-10: 1에서 10까지 해당될 때
 * 1,5,10: 1, 5, 10에 해당될 때
 * * /15: 매 15분 마다 (중간에 띄어쓰기 없이)
 * 10/15: 10분 부터 매 15분 마다
 */
@Component
class IotReservParserV1 {
    // Global Field
    @Autowired private lateinit var nodeReservRepo: NodeReservRepo


    fun getReservAction(nodeReserv: NodeReserv): List<NodeAction> {
        // Init
        val listNodeReservAction = nodeReserv.nodeReservActions
        val listNodeAction: MutableList<NodeAction> = ArrayList()

        // Process
        for (entity in listNodeReservAction)
            listNodeAction.add(entity.nodeAction)

        // Return
        return listNodeAction
    }

    fun getEnableReserv(): List<NodeReserv> {
        // Return
        return nodeReservRepo.findByEnableStatus(EnableStatusRule.ENABLE)
    }

    fun parseEntity(nodeReserv: NodeReserv): Boolean? {
        // Init
        val cal: Calendar = Calendar.getInstance()
        var strNow = cal[Calendar.MINUTE].toString() + " " // 분
        strNow += cal[Calendar.HOUR_OF_DAY].toString() + " " // 시간
        strNow += cal[Calendar.DAY_OF_MONTH].toString() + " " // 일
        strNow += (cal[Calendar.MONTH] + 1).toString() + " " // 월
        strNow += (cal[Calendar.DAY_OF_WEEK] - 1).toString() + "" // 요일
        val arrNow = strNow.split(" ").toTypedArray()
        val arrExpression = nodeReserv.expression.split(" ").toTypedArray()

        // Exception
        if (arrExpression.size != 5)
            return null

        // Parse
        for (i in arrNow.indices) {
            // Init
            val isSuccess = checkColumn(arrNow[i], arrExpression[i])

            // Check
            if (isSuccess != true)
                return isSuccess
        }

        // Return
        return true
    }

    private fun checkColumn(now: String, expression: String): Boolean? {
        if (expression == "*") // 와일드카드 표현식이면
            return true // 일치 판정
        else if (expression == now) // 현재 값과 표현식 값이 완전히 동일하면
            return true // 일치 판정
        else if (expression.contains(",")) {
            // Init
            val arrValue = expression.split(",").toTypedArray()

            // Parse
            for (value in arrValue)
                if (now == value)
                    return true
        } else if (expression.contains("-")) {
            // Init
            val arrValue = expression.split("-").toTypedArray()

            // Exception
            if (arrValue.size != 2)
                return null

            // Parse
            try {
                val intNow = Integer.valueOf(now)
                val intStart = Integer.valueOf(arrValue[0])
                val intEnd = Integer.valueOf(arrValue[1])
                if (intNow in intStart..intEnd) // 현재 시각이 표현식 범위 안에 있으면
                    return true // 일치 판정
            } catch (ex: Exception) {
                return null // 숫자 변환 불가능하면 표현식 에러 판정
            }
        } else if (expression.contains("/")) {
            // Field
            val intNow: Int
            val intStart: Int
            val intExpression: Int

            // Init
            val arrValue = expression.split("/").toTypedArray()

            // Exception
            if (arrValue.size != 2)
                return null // 표현식 에러

            // Parse
            try {
                intStart = if (arrValue[0] == "*")
                    0
                else
                    Integer.valueOf(arrValue[0])
                intNow = Integer.valueOf(now)
                intExpression = Integer.valueOf(arrValue[1])
            } catch (ex: Exception) {
                return null
            }
            if (intNow < intStart) // 현재 시각이 시작 시각보다 작으면
                return false // 불일치 판정
            if ((intNow - intStart) % intExpression == 0) // 현재 시각에서 시작 시각을 빼고 표현식 주기로 나눈 나머지가 없으면
                return true // 일치 판정
        } else {
            return try {
                Integer.valueOf(expression) // 표현식이 숫자인지 변환해보고
                false // 숫자면 불일치 판정
            } catch (ex: Exception) {
                null // 숫자가 아니면 표현식 에러 판정
            }
        }

        // Return
        return false
    }
}