package net.softbell.bsh.iot.component.v1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeItem;
import net.softbell.bsh.domain.entity.NodeItemHistory;
import net.softbell.bsh.domain.entity.NodeTrigger;
import net.softbell.bsh.domain.entity.NodeTriggerOccurAction;
import net.softbell.bsh.domain.entity.NodeTriggerRestoreAction;
import net.softbell.bsh.domain.repository.NodeItemHistoryRepo;
import net.softbell.bsh.domain.repository.NodeItemRepo;
import net.softbell.bsh.domain.repository.NodeTriggerRepo;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 트리거 파서 컴포넌트 v1
 * 표현식: {아이템ID.내장함수(매개변수)}==값 and ~~
 * [1.last(0)==0 and 2.last(0)!=0] or 3.last(0)>0
 */
@Slf4j
@AllArgsConstructor
@Component
public class IotTriggerParserCompV1
{
	// Global Field
	private final NodeTriggerRepo nodeTriggerRepo;
	private final NodeItemRepo nodeItemRepo;
	private final NodeItemHistoryRepo nodeItemHistoryRepo;
	
	@AllArgsConstructor
	@Getter
	enum BuiltInFunction
	{
		LAST("last"), // 마지막 값 조회
		MAX("max"), // 최대 값 조회
		MIN("min"), // 최소 값 조회
		AVG("avg"), // 평균 값 조회
		DIFF("diff"), // last(0)과 last(1)의 차이 여부(1 or 0)
		CHANGE("change"), // last(0) - last(1) 의 값
		ABSCHANGE("abschange"), // change의 절대값
		DELTA("delta"); // 최근 n 초 간 max - min 값

		private String value;
	    
	    public static BuiltInFunction ofValue(String value)
	    {
	    	for (BuiltInFunction funcType : values())
	            if (funcType.value.equals(value))
	                return funcType;
	    	
	    	return null;
	    }
	}
	
	@AllArgsConstructor
	@Getter
	enum RelationalOperatorType
	{
		EQ("=="), // = : Equal
		NE("!="), // != : Not Equal
		GE(">="), // >= : Greater Equal
		GT(">"), // > : Greater Then
		LE("<="), // <= : Little Equal
		LT("<"); // < : Little Then

		private String value;
	    
	    public static RelationalOperatorType ofValue(String value)
	    {
	    	for (RelationalOperatorType opType : values())
	            if (opType.value.equals(value))
	                return opType;
	    	
	    	return null;
	    }
	}
	
	@AllArgsConstructor
	@Getter
	enum LogicalOperatorType
	{
		AND("and"), // &&
		OR("or"); // ||

		private String value;
	    
	    public static LogicalOperatorType ofValue(String value)
	    {
	    	for (LogicalOperatorType opType : values())
	            if (opType.value.equals(value))
	                return opType;
	    	
	    	return null;
	    }
	}
	
	public List<NodeAction> getTriggerAction(NodeTrigger nodeTrigger, boolean isOccur)
	{
		// Field
		List<NodeAction> listNodeAction;
		
		// Init
		listNodeAction = new ArrayList<NodeAction>();
		
		// Process
		if (isOccur)
			for (NodeTriggerOccurAction entity : nodeTrigger.getNodeTriggerOccurActions())
				listNodeAction.add(entity.getNodeAction());
		else
			for (NodeTriggerRestoreAction entity : nodeTrigger.getNodeTriggerRestoreActions())
				listNodeAction.add(entity.getNodeAction());

		// Return
		return listNodeAction;
	}
	
	public List<NodeTrigger> convTrigger(NodeItem nodeItem)
	{
		// Field
		List<NodeTrigger> listNodeTrigger;
		String expression;
		
		// Exception
		if (nodeItem == null)
			return null;
		
		// Init
		expression = "{";
		expression += nodeItem.getItemId();
		expression += ".";
		
		// Process
		listNodeTrigger = nodeTriggerRepo.findByEnableStatusAndExpressionContaining(EnableStatusRule.ENABLE, expression);
		
		// Return
		return listNodeTrigger;
	}
	
	public Boolean parseEntity(NodeTrigger nodeTrigger)
	{
		// Field
		int intParentCount, intLoopCount;
		String rawExpression, parseExpression, resultExpression;
		
		// Init
		intParentCount = 10; // 괄호 최대 10개로 제한
		intLoopCount = 10; // 괄호 내 아이템 최대 10개로 제한
		rawExpression = nodeTrigger.getExpression();
		resultExpression = rawExpression;
		
		log.info("표현식 분석 시작: " + rawExpression);
		
		// Process - []
		while (intParentCount-- > 0)
		{
			// Field
			int idxParentStart, idxParentEnd;
			
			// Init
			idxParentStart = resultExpression.indexOf("[");
			idxParentEnd = resultExpression.indexOf("]");
			if (idxParentStart == -1)
				parseExpression = resultExpression;
			else
				parseExpression = resultExpression.substring(idxParentStart + 1, idxParentEnd);
			
			// Process - Item
			parseExpression = procItem(parseExpression, intLoopCount);
			
			// Exception
			if (parseExpression == null)
				return null;
			
			// Post Process
			parseExpression = parseLogical(parseExpression); // 논리 연산자 계산
			
			// Replace
			if (idxParentStart == -1)
				resultExpression = parseExpression;
			else
				resultExpression = resultExpression.replace(resultExpression.substring(idxParentStart, idxParentEnd + 1), parseExpression);
			
			// Finish Check
			if (resultExpression.length() <= 1)
				break;
		}
		
		log.info("표현식 분석 끝: " + resultExpression); // TODO
		
		// Return
		if (resultExpression.equals("1")) // 트리거 활성화
			return true;
		else if (resultExpression.equals("0")) // 트리거 복구
			return false;
		return null; // 파싱 에러
	}
	
	private String procItem(String parseExpression, int intLoopCount)
	{
		while (intLoopCount-- > 0)
		{
			// Field
			int idxItemStart, idxItemIdEnd, idxParamStart, idxParamEnd, idxItemEnd, idxOperator, idxCompOperator;
			long itemId;
			BuiltInFunction funcType;
			RelationalOperatorType relOpType;
			String strFunc, strParam, strItemId, strRelValue, strResult;
			Double itemStatus, relValue;
			Boolean isResult;
			
			// Init
			idxOperator = -1;
			idxCompOperator = -1;
			relOpType = null;
			
			// Parse
			idxItemStart = parseExpression.indexOf("{"); // 표현식 값 치환 시작부분 탐색
			idxItemIdEnd = parseExpression.indexOf(".", idxItemStart); // 표현식 내장함수 시작부분 탐색
			idxParamStart = parseExpression.indexOf("(", idxItemIdEnd); // 표현식 내장함수 매개변수 시작부분 탐색
			idxParamEnd = parseExpression.indexOf(")", idxParamStart); // 표현식 내장함수 매개변수 끝부분 탐색
			idxItemEnd = parseExpression.indexOf("}", idxParamEnd); // 표현식 값 치환 닫는부분 탐색
			
			for (RelationalOperatorType opType : RelationalOperatorType.values())
			{
				int idxTemp = parseExpression.indexOf(opType.value, idxItemEnd); // 표현식 관계 연산자 탐색
				
				if (idxTemp != -1 && (idxOperator == -1 || idxOperator > idxTemp))
				{
					idxOperator = idxTemp;
					relOpType = opType;
				}
			}
			for (LogicalOperatorType opType : LogicalOperatorType.values())
			{
				int idxTemp = parseExpression.indexOf(opType.value, idxOperator); // 표현식 논리 연산자 탐색
				
				if (idxTemp != -1 && (idxCompOperator == -1 || idxCompOperator > idxTemp))
					idxCompOperator = idxTemp;
			}
			
			// Exception
			if (idxItemStart == -1)
				break;
			else if (idxItemStart == -1 || idxItemIdEnd == -1 || idxParamStart == -1 || idxParamEnd == -1)
				return null;
			if (relOpType == null)
				return null;
			
			// Process
			strItemId = parseExpression.substring(idxItemStart + 1, idxItemIdEnd);
			strFunc = parseExpression.substring(idxItemIdEnd + 1, idxParamStart);
			strParam = parseExpression.substring(idxParamStart + 1, idxParamEnd);
			if (idxCompOperator == -1)
				strRelValue = parseExpression.substring(idxOperator + relOpType.value.length());
			else
				strRelValue = parseExpression.substring(idxOperator + relOpType.value.length(), idxCompOperator);
			
			try {
				itemId = Long.valueOf(strItemId);
				relValue = Double.valueOf(strRelValue);
				funcType = BuiltInFunction.ofValue(strFunc);
			} catch (Exception ex) {
				log.error("무슨 에러? " + ex.getMessage());
				return null;
			}
			
			// Load
			itemStatus = getItemStatus(itemId, funcType, strParam);
			
			// Relational Process
			isResult = procRelational(relOpType, itemStatus, relValue); // 관계 연산자 분석
			
			// Post Process
			if (isResult == null)
				return null;
			else if (isResult)
				strResult = "1";
			else
				strResult = "0";
			
			// Replace
			if (idxCompOperator == -1)
				parseExpression = parseExpression.replace(parseExpression.substring(idxItemStart), strResult);
			else
				parseExpression = parseExpression.replace(parseExpression.substring(idxItemStart, idxCompOperator - 1), strResult);
		}
		
		// Return
		return parseExpression;
	}
	
	private Double getItemStatus(long itemId, BuiltInFunction funcType, String param)
	{
		// Field
		Optional<NodeItem> optNodeItem;
		NodeItem nodeItem;
		Double result = null;
		Date dateStart = null;
		Pageable curPage = null;
		int intParam = 0;
		
		// Init
		optNodeItem = nodeItemRepo.findById(itemId);
		
		// Exception
		if (!optNodeItem.isPresent())
			return null;
		
		// Load
		nodeItem = optNodeItem.get();
		
		// Process - Param Parse
		// No Parameter Function
		if (funcType == BuiltInFunction.DIFF || 
				funcType == BuiltInFunction.CHANGE || 
				funcType == BuiltInFunction.ABSCHANGE)
			intParam = 0; // Set Parameter
		else
		{
			try {
				intParam = Integer.valueOf(param);
			} catch (Exception ex) {
				log.error("표현식 매개변수 정수 변환 에러: " + param);
				return null;
			}
		}
		
		// Process - DB 1 Record Find
		if (funcType == BuiltInFunction.LAST ||
				funcType == BuiltInFunction.DIFF ||
				funcType == BuiltInFunction.CHANGE ||
				funcType == BuiltInFunction.ABSCHANGE)
		{
			// Field
			Page<NodeItemHistory> pageNodeItemHistory;
			List<NodeItemHistory> listNodeItemHistory;
			
			// Init
			if (funcType == BuiltInFunction.LAST)
				curPage = PageRequest.of(intParam, 1); // Page Set
			else
				curPage = PageRequest.of(0, 2); // Page Set
			
			// Load
			pageNodeItemHistory = nodeItemHistoryRepo.findByNodeItemOrderByItemHistoryIdDesc(nodeItem, curPage);
			
			// Exception
			if (pageNodeItemHistory == null || pageNodeItemHistory.isEmpty())
				return null;
			
			// Post Load
			listNodeItemHistory = pageNodeItemHistory.getContent();
			
			// Process - Last
			if (funcType == BuiltInFunction.LAST)
				result = listNodeItemHistory.get(0).getItemStatus();
			else
			{
				// Process - Other
				// Field
				double lastStatus;
				double beforeStatus;
				
				// Init
				lastStatus = listNodeItemHistory.get(0).getItemStatus();
				beforeStatus = listNodeItemHistory.get(1).getItemStatus();
				
				// Process
				switch (funcType)
				{
					case DIFF:
						// TODO
						if (lastStatus != beforeStatus)
							result = 1D;
						else
							result = 0D;
						break;
						
					case CHANGE:
						result = lastStatus - beforeStatus;
						break;
						
					case ABSCHANGE:
						result = Math.abs(lastStatus - beforeStatus);
						break;
						
					default:
				}
			}
		}
		// Date Function Find
		else if (funcType == BuiltInFunction.AVG || 
				funcType == BuiltInFunction.MIN || 
				funcType == BuiltInFunction.MAX ||
				funcType == BuiltInFunction.DELTA)
		{
			// Field
			Calendar calendar = Calendar.getInstance();
			
			// Init
	    	calendar.add(Calendar.SECOND, intParam);
	    	dateStart = calendar.getTime();
	    	
	    	// Process
	    	switch (funcType)
			{
				case AVG:
					result = nodeItemHistoryRepo.avgByNodeItem(nodeItem, dateStart);
					break;
					
				case MIN:
					result = nodeItemHistoryRepo.minByNodeItem(nodeItem, dateStart);
					break;
					
				case MAX:
					result = nodeItemHistoryRepo.maxByNodeItem(nodeItem, dateStart);
					break;
					
				case DELTA:
					// Field
					double itemMax, itemMin;
					
					// Init
					itemMax = nodeItemHistoryRepo.maxByNodeItem(nodeItem, dateStart);
					itemMin = nodeItemHistoryRepo.minByNodeItem(nodeItem, dateStart);
					
					// Process
					result = itemMax - itemMin;
					break;
					
				default:
			}
		}
		else
			return null; // Not Defined
		
		// Return
		return result;
	}
	
	private Boolean procRelational(RelationalOperatorType relOpType, double itemStatus, double relValue)
	{
		// Field
		Boolean isResult;
		
		// Process
		switch (relOpType)
		{
			case EQ:
				isResult = itemStatus == relValue;
				break;
			
			case NE:
				isResult = itemStatus != relValue;
				break;
				
			case GT:
				isResult = itemStatus > relValue;
				break;
				
			case GE:
				isResult = itemStatus >= relValue;
				break;
				
			case LT:
				isResult = itemStatus < relValue;
				break;
				
			case LE:
				isResult = itemStatus <= relValue;
				break;
				
			default:
				return null;
		}
		
		// Return
		return isResult;
	}
	
	private String parseLogical(String parseExpression)
	{
		for (LogicalOperatorType opType : LogicalOperatorType.values())
		{
			// Field
			int intLogicalCount = 10;

			// Logical Process
			while (intLogicalCount-- > 0)
			{
				// Field
				int idxTemp;
				String strVal1, strVal2, strResult;
				Boolean val1, val2, isResult;
				
				// Init
				val1 = false;
				val2 = false;
				idxTemp = parseExpression.indexOf(opType.value); // 표현식 논리 연산자 탐색
				
				// Exception
				if (idxTemp == -1)
					break;
				
				// Load
				strVal1 = parseExpression.substring(idxTemp - 2, idxTemp - 1);
				strVal2 = parseExpression.substring(idxTemp + opType.value.length() + 1, idxTemp + opType.value.length() + 2);

				try {
					if (strVal1.equals("1"))
						val1 = true;
					if (strVal2.equals("1"))
						val2 = true;
				} catch (Exception ex) {
					log.error("파싱 에러: " + ex.getMessage());
					return null;
				}
				
				// Process
				switch (opType)
				{
					case AND:
						isResult = val1 && val2;
						break;
						
					case OR:
						isResult = val1 || val2;
						break;

					default:
						log.error("여기가 와지나?");
						return null;
				}
				if (isResult)
					strResult = "1";
				else
					strResult = "0";
				
				// Replace
				parseExpression = parseExpression.replace(parseExpression.substring(idxTemp - 2, idxTemp + opType.value.length() + 2), strResult);
			}
		}
		
		// Return
		return parseExpression;
	}
}
