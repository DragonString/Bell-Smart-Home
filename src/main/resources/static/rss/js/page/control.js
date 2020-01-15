/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 컨트롤 페이지 자바스크립트
 */

function setItem(value)
{
	sendAJAX('/api/rest/v1/iot/control/item/set/3', { "value" : value });
}