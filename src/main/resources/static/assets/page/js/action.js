/**
 * @author : Bell(bell@softbell.net)
 * @description : 액션 페이지 자바스크립트
 */

function setItem(id)
{
	sendAJAX('/api/rest/v1/iot/action/exec/' + id, null);
}