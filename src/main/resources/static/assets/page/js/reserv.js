/**
 * @author : Bell(bell@softbell.net)
 * @description : 노드 정보 페이지 자바스크립트
 */

// 아이템 상태 변경
function setReserv(id, status)
{
	sendAJAX('/api/rest/v1/reserv/status/' + id, { "status" : status });
}

$(".reserv").change(function()
	{
		if($(this).is(":checked"))
			setReserv($(this).attr("value"), true);
		else
			setReserv($(this).attr("value"), false);
	});
