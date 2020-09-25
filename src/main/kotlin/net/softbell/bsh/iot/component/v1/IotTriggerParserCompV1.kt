package net.softbell.bsh.iot.component.v1

import net.softbell.bsh.domain.EnableStatusRule
import net.softbell.bsh.domain.TriggerStatusRule
import net.softbell.bsh.domain.entity.NodeAction
import net.softbell.bsh.domain.entity.NodeItem
import net.softbell.bsh.domain.entity.NodeItemHistory
import net.softbell.bsh.domain.entity.NodeTrigger
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo
import net.softbell.bsh.domain.repository.NodeItemRepo
import net.softbell.bsh.domain.repository.NodeTriggerRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.util.*

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 트리거 파서 컴포넌트 v1
 * 표현식: {아이템ID.내장함수(매개변수)}==값 and ~~
 * [1.last(0)==0 and 2.last(0)!=0] or 3.last(0)>0
 */
@Component
class IotTriggerParserCompV1 {
    // Global Field
    @Autowired lateinit var nodeTriggerRepo: NodeTriggerRepo
    @Autowired lateinit var nodeItemRepo: NodeItemRepo
    @Autowired lateinit var nodeItemHistoryRepo: NodeItemHistoryRepo

    internal enum class BuiltInFunction {
        LAST("last"),  // 마지막 값 조회
        MAX("max"),  // 최대 값 조회
        MIN("min"),  // 최소 값 조회
        AVG("avg"),  // 평균 값 조회
        DIFF("diff"),  // last(0)과 last(1)의 차이 여부(1 or 0)
        CHANGE("change"),  // last(0) - last(1) 의 값
        ABSCHANGE("abschange"),  // change의 절대값
        DELTA("delta");

        // 최근 n 초 간 max - min 값
        private val value: String? = null

        companion object {
            fun ofValue(value: String): BuiltInFunction? {
                for (funcType in values()) if (funcType.value == value) return funcType
                return null
            }
        }
    }

    internal enum class RelationalOperatorType {
        EQ("=="),  // = : Equal
        NE("!="),  // != : Not Equal
        GE(">="),  // >= : Greater Equal
        GT(">"),  // > : Greater Then
        LE("<="),  // <= : Little Equal
        LT("<");

        // < : Little Then
        val value: String? = null

        companion object {
            fun ofValue(value: String): RelationalOperatorType? {
                for (opType in values()) if (opType.value == value) return opType
                return null
            }
        }
    }

    internal enum class LogicalOperatorType {
        AND("and"),  // &&
        OR("or");

        // ||
        val value: String? = null

        companion object {
            fun ofValue(value: String): LogicalOperatorType? {
                for (opType in values()) if (opType.value == value) return opType
                return null
            }
        }
    }

    fun getTriggerAction(nodeTrigger: NodeTrigger?): List<NodeAction> {
        // Field
        val listNodeAction: MutableList<NodeAction>

        // Init
        listNodeAction = ArrayList()
        log.info(BellLog.getLogHead() + "트리거 액션 로드 (" + nodeTrigger.getLastStatus() + ") - " + nodeTrigger.getDescription())

        // Process
        for (entity in nodeTrigger.getNodeTriggerActions()) when (nodeTrigger.getLastStatus()) {
            OCCUR -> if (entity.getTriggerStatus() !== TriggerStatusRule.ERROR &&
                    entity.getTriggerStatus() !== TriggerStatusRule.RESTORE) listNodeAction.add(entity.getNodeAction())
            RESTORE -> if (entity.getTriggerStatus() !== TriggerStatusRule.ERROR &&
                    entity.getTriggerStatus() !== TriggerStatusRule.OCCUR) listNodeAction.add(entity.getNodeAction())
            ERROR -> if (entity.getTriggerStatus() === TriggerStatusRule.ALL ||
                    entity.getTriggerStatus() === TriggerStatusRule.ERROR) listNodeAction.add(entity.getNodeAction())
            else -> {
            }
        }

        // Return
        return listNodeAction
    }

    fun convTrigger(nodeItem: NodeItem?): List<NodeTrigger?>? {
        // Field
        val listNodeTrigger: List<NodeTrigger?>?
        var expression: String?

        // Exception
        if (nodeItem == null) return null

        // Init
        expression = "{"
        expression += nodeItem.getItemId()
        expression += "."

        // Process
        listNodeTrigger = nodeTriggerRepo!!.findByEnableStatusAndExpressionContaining(EnableStatusRule.ENABLE, expression)

        // Return
        return listNodeTrigger
    }

    fun parseEntity(nodeTrigger: NodeTrigger?): Boolean? {
        // Field
        var intParentCount: Int
        val intLoopCount: Int
        val rawExpression: String
        var parseExpression: String?
        var resultExpression: String?

        // Init
        intParentCount = 10 // 괄호 최대 10개로 제한
        intLoopCount = 10 // 괄호 내 아이템 최대 10개로 제한
        rawExpression = nodeTrigger.getExpression()
        resultExpression = rawExpression

//		log.info("표현식 분석 시작: " + rawExpression);

        // Process - []
        while (intParentCount-- > 0) {
            // Field
            var idxParentStart: Int
            var idxParentEnd: Int

            // Init
            idxParentStart = resultExpression!!.indexOf("[")
            idxParentEnd = resultExpression.indexOf("]")
            parseExpression = if (idxParentStart == -1) resultExpression else resultExpression.substring(idxParentStart + 1, idxParentEnd)

            // Process - Item
            parseExpression = procItem(parseExpression, intLoopCount)

            // Exception
            if (parseExpression == null) return null

            // Post Process
            parseExpression = parseLogical(parseExpression) // 논리 연산자 계산

            // Replace
            if (idxParentStart == -1) resultExpression = parseExpression else resultExpression = resultExpression.replace(resultExpression.substring(idxParentStart, idxParentEnd + 1), parseExpression)

            // Finish Check
            if (resultExpression!!.length <= 1) break
        }

//		log.info("표현식 분석 끝: " + resultExpression); // TODO

        // Return
        if (resultExpression == "1") // 트리거 활성화
            return true else if (resultExpression == "0") // 트리거 복구
            return false
        return null // 파싱 에러
    }

    private fun procItem(parseExpression: String?, intLoopCount: Int): String? {
        var parseExpression = parseExpression
        var intLoopCount = intLoopCount
        while (intLoopCount-- > 0) {
            // Field
            var idxItemStart: Int
            var idxItemIdEnd: Int
            var idxParamStart: Int
            var idxParamEnd: Int
            var idxItemEnd: Int
            var idxOperator: Int
            var idxCompOperator: Int
            var itemId: Long
            var funcType: BuiltInFunction?
            var relOpType: RelationalOperatorType?
            var strFunc: String
            var strParam: String
            var strItemId: String
            var strRelValue: String
            var strResult: String
            var itemStatus: Double?
            var relValue: Double
            var isResult: Boolean?

            // Init
            idxOperator = -1
            idxCompOperator = -1
            relOpType = null

            // Parse
            idxItemStart = parseExpression!!.indexOf("{") // 표현식 값 치환 시작부분 탐색
            idxItemIdEnd = parseExpression.indexOf(".", idxItemStart) // 표현식 내장함수 시작부분 탐색
            idxParamStart = parseExpression.indexOf("(", idxItemIdEnd) // 표현식 내장함수 매개변수 시작부분 탐색
            idxParamEnd = parseExpression.indexOf(")", idxParamStart) // 표현식 내장함수 매개변수 끝부분 탐색
            idxItemEnd = parseExpression.indexOf("}", idxParamEnd) // 표현식 값 치환 닫는부분 탐색
            for (opType in RelationalOperatorType.values()) {
                val idxTemp = parseExpression.indexOf(opType.value!!, idxItemEnd) // 표현식 관계 연산자 탐색
                if (idxTemp != -1 && (idxOperator == -1 || idxOperator > idxTemp)) {
                    idxOperator = idxTemp
                    relOpType = opType
                }
            }
            for (opType in LogicalOperatorType.values()) {
                val idxTemp = parseExpression.indexOf(opType.value!!, idxOperator) // 표현식 논리 연산자 탐색
                if (idxTemp != -1 && (idxCompOperator == -1 || idxCompOperator > idxTemp)) idxCompOperator = idxTemp
            }

            // Exception
            if (idxItemStart == -1) break else if (idxItemStart == -1 || idxItemIdEnd == -1 || idxParamStart == -1 || idxParamEnd == -1) return null
            if (relOpType == null) return null

            // Process
            strItemId = parseExpression.substring(idxItemStart + 1, idxItemIdEnd)
            strFunc = parseExpression.substring(idxItemIdEnd + 1, idxParamStart)
            strParam = parseExpression.substring(idxParamStart + 1, idxParamEnd)
            strRelValue = if (idxCompOperator == -1) parseExpression.substring(idxOperator + relOpType.value!!.length) else parseExpression.substring(idxOperator + relOpType.value!!.length, idxCompOperator)
            try {
                itemId = java.lang.Long.valueOf(strItemId)
                relValue = java.lang.Double.valueOf(strRelValue)
                funcType = BuiltInFunction.ofValue(strFunc)
            } catch (ex: Exception) {
                log.error("무슨 에러? " + ex.message)
                return null
            }

            // Load
            itemStatus = getItemStatus(itemId, funcType, strParam)

            // Relational Process
            isResult = procRelational(relOpType, itemStatus!!, relValue) // 관계 연산자 분석

            // Post Process
            strResult = if (isResult == null) return null else if (isResult) "1" else "0"

            // Replace
            parseExpression = if (idxCompOperator == -1) parseExpression.replace(parseExpression.substring(idxItemStart), strResult) else parseExpression.replace(parseExpression.substring(idxItemStart, idxCompOperator - 1), strResult)
        }

        // Return
        return parseExpression
    }

    private fun getItemStatus(itemId: Long, funcType: BuiltInFunction?, param: String): Double? {
        // Field
        val optNodeItem: Optional<NodeItem?>
        val nodeItem: NodeItem
        var result: Double? = null
        var dateStart: Date? = null
        var curPage: Pageable? = null
        var intParam = 0

        // Init
        optNodeItem = nodeItemRepo!!.findById(itemId)

        // Exception
        if (!optNodeItem.isPresent) return null

        // Load
        nodeItem = optNodeItem.get()

        // Process - Param Parse
        // No Parameter Function
        intParam = if (funcType == BuiltInFunction.DIFF || funcType == BuiltInFunction.CHANGE || funcType == BuiltInFunction.ABSCHANGE) 0 // Set Parameter
        else {
            try {
                Integer.valueOf(param)
            } catch (ex: Exception) {
                log.error("표현식 매개변수 정수 변환 에러: $param")
                return null
            }
        }

        // Process - DB 1 Record Find
        if (funcType == BuiltInFunction.LAST || funcType == BuiltInFunction.DIFF || funcType == BuiltInFunction.CHANGE || funcType == BuiltInFunction.ABSCHANGE) {
            // Field
            val pageNodeItemHistory: Page<NodeItemHistory?>?
            val listNodeItemHistory: List<NodeItemHistory?>

            // Init
            curPage = if (funcType == BuiltInFunction.LAST) PageRequest.of(intParam, 1) // Page Set
            else PageRequest.of(0, 2) // Page Set

            // Load
            pageNodeItemHistory = nodeItemHistoryRepo!!.findByNodeItemOrderByItemHistoryIdDesc(nodeItem, curPage)

            // Exception
            if (pageNodeItemHistory == null || pageNodeItemHistory.isEmpty) return null

            // Post Load
            listNodeItemHistory = pageNodeItemHistory.content

            // Process - Last
            if (funcType == BuiltInFunction.LAST) result = listNodeItemHistory[0].getItemStatus() else {
                // Process - Other
                // Exception
                if (listNodeItemHistory.size < 2) return null

                // Field
                val lastStatus: Double
                val beforeStatus: Double

                // Init
                lastStatus = listNodeItemHistory[0].getItemStatus()
                beforeStatus = listNodeItemHistory[1].getItemStatus()
                when (funcType) {
                    BuiltInFunction.DIFF ->                        // TODO
                        result = if (lastStatus != beforeStatus) 1.0 else 0.0
                    BuiltInFunction.CHANGE -> result = lastStatus - beforeStatus
                    BuiltInFunction.ABSCHANGE -> result = Math.abs(lastStatus - beforeStatus)
                    else -> {
                    }
                }
            }
        } else if (funcType == BuiltInFunction.AVG || funcType == BuiltInFunction.MIN || funcType == BuiltInFunction.MAX || funcType == BuiltInFunction.DELTA) {
            // Field
            val calendar = Calendar.getInstance()

            // Init
            calendar.add(Calendar.SECOND, intParam)
            dateStart = calendar.time
            when (funcType) {
                BuiltInFunction.AVG -> result = nodeItemHistoryRepo!!.avgByNodeItem(nodeItem, dateStart)
                BuiltInFunction.MIN -> result = nodeItemHistoryRepo!!.minByNodeItem(nodeItem, dateStart)
                BuiltInFunction.MAX -> result = nodeItemHistoryRepo!!.maxByNodeItem(nodeItem, dateStart)
                BuiltInFunction.DELTA -> {
                    // Field
                    val itemMax: Double
                    val itemMin: Double

                    // Init
                    itemMax = nodeItemHistoryRepo!!.maxByNodeItem(nodeItem, dateStart)!!
                    itemMin = nodeItemHistoryRepo.minByNodeItem(nodeItem, dateStart)!!

                    // Process
                    result = itemMax - itemMin
                }
                else -> {
                }
            }
        } else return null // Not Defined

        // Return
        return result
    }

    private fun procRelational(relOpType: RelationalOperatorType, itemStatus: Double, relValue: Double): Boolean? {
        // Field
        val isResult: Boolean
        isResult = when (relOpType) {
            RelationalOperatorType.EQ -> itemStatus == relValue
            RelationalOperatorType.NE -> itemStatus != relValue
            RelationalOperatorType.GT -> itemStatus > relValue
            RelationalOperatorType.GE -> itemStatus >= relValue
            RelationalOperatorType.LT -> itemStatus < relValue
            RelationalOperatorType.LE -> itemStatus <= relValue
            else -> return null
        }

        // Return
        return isResult
    }

    private fun parseLogical(parseExpression: String): String? {
        var parseExpression = parseExpression
        for (opType in LogicalOperatorType.values()) {
            // Field
            var intLogicalCount = 10

            // Logical Process
            while (intLogicalCount-- > 0) {
                // Field
                var idxTemp: Int
                var strVal1: String
                var strVal2: String
                var strResult: String
                var val1: Boolean
                var val2: Boolean
                var isResult: Boolean

                // Init
                val1 = false
                val2 = false
                idxTemp = parseExpression.indexOf(opType.value!!) // 표현식 논리 연산자 탐색

                // Exception
                if (idxTemp == -1) break

                // Load
                strVal1 = parseExpression.substring(idxTemp - 2, idxTemp - 1)
                strVal2 = parseExpression.substring(idxTemp + opType.value.length + 1, idxTemp + opType.value.length + 2)
                try {
                    if (strVal1 == "1") val1 = true
                    if (strVal2 == "1") val2 = true
                } catch (ex: Exception) {
                    log.error("파싱 에러: " + ex.message)
                    return null
                }
                isResult = when (opType) {
                    LogicalOperatorType.AND -> val1 && val2
                    LogicalOperatorType.OR -> val1 || val2
                    else -> {
                        log.error("여기가 와지나?")
                        return null
                    }
                }
                strResult = if (isResult) "1" else "0"

                // Replace
                parseExpression = parseExpression.replace(parseExpression.substring(idxTemp - 2, idxTemp + opType.value.length + 2), strResult)
            }
        }

        // Return
        return parseExpression
    }
}