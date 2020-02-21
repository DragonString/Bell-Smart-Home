package net.softbell.bsh.iot.component.v1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import net.softbell.bsh.domain.EnableStatusRule;
import net.softbell.bsh.domain.entity.NodeAction;
import net.softbell.bsh.domain.entity.NodeReserv;
import net.softbell.bsh.domain.entity.NodeReservAction;
import net.softbell.bsh.domain.repository.NodeReservRepo;

/**
 * @Author : Bell(bell@softbell.net)
 * @Description : IoT 예약 표현식 파서 v1
 * 리눅스의 cron 표현식과 동일하게 사용
 * 분(0-59) 시간(0-23) 일(1-13) 월(1-12) 요일(0-7)

 * 각자 위치에 사용 가능한 표현
 * *: 항상
 * 1: 1에 해당될 때
 * 1-10: 1에서 10까지 해당될 때
 * 1,5,10: 1, 5, 10에 해당될 때
 * * /15: 매 15분 마다 (중간에 띄어쓰기 없이)
 * 10/15: 10분 부터 매 15분 마다
 */
@RequiredArgsConstructor
@Component
public class IotReservParserV1
{
	// Global Field
	private final NodeReservRepo nodeReservRepo;
	
	public List<NodeAction> getReservAction(NodeReserv nodeReserv)
	{
		// Field
		List<NodeReservAction> listNodeReservAction;
		List<NodeAction> listNodeAction;
		
		// Init
		listNodeReservAction = nodeReserv.getNodeReservActions();
		listNodeAction = new ArrayList<NodeAction>();
		
		// Process
		for (NodeReservAction entity : listNodeReservAction)
			listNodeAction.add(entity.getNodeAction());
		
		// Return
		return listNodeAction;
	}
	
	public List<NodeReserv> getEnableReserv()
	{
		// Return
		return nodeReservRepo.findByEnableStatus(EnableStatusRule.ENABLE);
	}
	
	public Boolean parseEntity(NodeReserv nodeReserv)
	{
		// Field
		String strNow;
		String[] arrNow;
		String[] arrExpression;
		Calendar cal;
		
		// Init
		cal = Calendar.getInstance();
		strNow = cal.get(Calendar.MINUTE) + " "; // 분
		strNow += cal.get(Calendar.HOUR_OF_DAY) + " "; // 시간
		strNow += cal.get(Calendar.DAY_OF_MONTH) + " "; // 일
		strNow += cal.get(Calendar.MONTH) + 1 + " "; // 월
		strNow += cal.get(Calendar.DAY_OF_WEEK) - 1 + ""; // 요일
		arrNow = strNow.split(" ");
		arrExpression = nodeReserv.getExpression().split(" ");
		
		// Exception
		if (arrExpression.length != 5)
			return null;
		
		// Parse
		for (int i = 0; i < arrNow.length; i++)
		{
			// Field
			Boolean isSuccess;
			
			// Init
			isSuccess = checkColumn(arrNow[i], arrExpression[i]);
			if (!isSuccess)
				return isSuccess;
		}
		
		// Return
		return true;
	}
	
	private Boolean checkColumn(String now, String expression)
	{
		if (expression.equals("*")) // 와일드카드 표현식이면
			return true; // 일치 판정
		else if (expression.equals(now)) // 현재 값과 표현식 값이 완전히 동일하면
			return true; // 일치 판정
		else
			if (expression.contains(","))
			{
				// Field
				String[] arrValue;
				
				// Init
				arrValue = expression.split(",");
				
				// Parse
				for (String value : arrValue)
					if (expression.equals(value))
						return true;
			}
			else if (expression.contains("-"))
			{
				// Field
				String[] arrValue;
				int intNow, intStart, intEnd;
				
				// Init
				arrValue = expression.split("-");
				
				// Exception
				if (arrValue.length != 2)
					return null;
				
				// Parse
				try {
					intNow = Integer.valueOf(now);
					intStart = Integer.valueOf(arrValue[0]);
					intEnd = Integer.valueOf(arrValue[1]);
					
					if (intNow >= intStart && intNow <= intEnd) // 현재 시각이 표현식 범위 안에 있으면
						return true; // 일치 판정
				} catch (Exception ex) {
					return null; // 숫자 변환 불가능하면 표현식 에러 판정
				}
			}
			else if (expression.contains("/"))
			{
				// Field
				String[] arrValue;
				int intNow, intStart, intExpression;
				
				// Init
				arrValue = expression.split("/");
				
				// Exception
				if (arrValue.length != 2)
					return null; // 표현식 에러
				
				// Parse
				try {
					if (arrValue[0].equals("*"))
						intStart = 0;
					else
						intStart = Integer.valueOf(arrValue[0]);
					intNow = Integer.valueOf(now);
					intExpression = Integer.valueOf(arrValue[1]);
				} catch (Exception ex) {
					return null;
				}
				if (intNow < intStart) // 현재 시각이 시작 시각보다 작으면
					return false; // 불일치 판정
				if ((intNow - intStart) % intExpression == 0) // 현재 시각에서 시작 시각을 빼고 표현식 주기로 나눈 나머지가 없으면
					return true; // 일치 판정
			}
			else
			{
				try {
					Integer.valueOf(expression); // 표현식이 숫자인지 변환해보고
					return false; // 숫자면 불일치 판정
				} catch (Exception ex) {
					return null; // 숫자가 아니면 표현식 에러 판정
				}
			}
		
		// Return
		return false;
	}
}
