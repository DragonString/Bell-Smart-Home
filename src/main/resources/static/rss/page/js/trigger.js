/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 정보 페이지 자바스크립트
 */

// 아이템 상태 변경
function setTrigger(id, status)
{
	sendAJAX('/api/rest/v1/trigger/status/' + id, { "status" : status });
}

$(".trigger").change(function()
	{
		if($(this).is(":checked"))
			setTrigger($(this).attr("value"), true);
		else
			setTrigger($(this).attr("value"), false);
	});
