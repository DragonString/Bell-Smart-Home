/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 액션 페이지 자바스크립트
 */

function setItem(id)
{
	sendAJAX('/api/rest/v1/iot/action/exec/' + id, null);
}