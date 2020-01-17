/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 노드 정보 페이지 자바스크립트
 */

function setItem(id, value)
{
	sendAJAX('/api/rest/v1/iot/control/item/set/' + id, { "value" : value });
}

$(".nodeItem").change(function()
	{
		if($(this).is(":checked"))
			setItem($(this).attr("value"), 1);
		else
			setItem($(this).attr("value"), 0);
	});