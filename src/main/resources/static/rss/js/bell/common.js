/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 방울의 자바스크립트 필수 라이브러리
 */

// Bootstrap Modal Init Focus
// 사용법: class 속성이 modal인 태그 안에 initfocus class 속성을 지정하면 모달이 열리자마자 자동으로 포커스 설정
$(document).ready(function()
{
	$('.modal').on('shown.bs.modal', function()
	{
		$('.initfocus').trigger('focus');
	});
});

/**
 * @param path 전송할 경로
 * @param params 전송할 데이터 맵
 * @param method 전송할 메소드 (default: POST)
 * @example post_to_url('/member/basket/remove', {'intProductId':productId});
 */
function sendData(path, params, ajax, method)
{
    method = method || "post";
    ajax = ajax || 0;
    
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);
    if (ajax != 0)
    	form.setAttribute("target", "dummyframe");
    
    // csrf
    var csrf_name = document.getElementById("csrf_data").getAttribute('name');
	var csrf_value = document.getElementById("csrf_data").getAttribute('value');
    var hiddenField = document.createElement("input");
    hiddenField.setAttribute("type", "hidden");
    hiddenField.setAttribute("name", csrf_name);
    hiddenField.setAttribute("value", csrf_value);
    form.appendChild(hiddenField);
    
    // custom value
    for(var key in params) {
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", key);
        hiddenField.setAttribute("value", params[key]);
        form.appendChild(hiddenField);
    }
    document.body.appendChild(form);
    form.submit();
}

/**
 * AJAX로 데이터 전송 (기존 form이 있어야됨)
 * @param path 전송할 URL
 * @param name form 이름 (id값으로 지정시: #test, class값으로 지정시: .test)
 * @param method GET or POST
 */
function sendAJAX (path, name, method)
{
	method = method || "post";
	
    $.ajax({
        url:path,
        type:method,
        data:$(name).serialize(),
        success:function(){
            alert("worked");
        }
    });
}

/**
 * Input Tag의 Value 길이 검사
 * @param objInput Input Tag Object
 * @param intMin 최소 길이
 * @param intMax 최대 길이
 * @returns 성공 여부
 */
function checkInputLength(objInput, intMin, intMax)
{
	// Field
	var isSuccess = false;
	
	// Process
	if (objInput.value.length != 0)
		if (objInput.value.length >= intMin && objInput.value.length <= intMax)
		{
			setInputValid(objInput, true);
			isSuccess = true; // 길이가 범위 안에 있음
		}
		else
			setInputValid(objInput, false);
	
	// Finish
	return isSuccess;
}

/**
 * Bootstrap Input Tag에 valid 디자인 설정
 * @param objInput Input Tag Object
 * @param isNormal 정상값 여부
 */
function setInputValid(objInput, isNormal)
{
	if (isNormal)
	{
		objInput.classList.remove('is-invalid');
		objInput.classList.add('is-valid');
	}
	else
	{
		objInput.classList.remove('is-valid');
		objInput.classList.add('is-invalid');
	}
}