/**
 * @Author : Bell(bell@softbell.net)
 * @Description : 회원 관련 JS
 */

function checkSignup(strFormId)
{
	// Field
	var isSuccess = true;
	var objUsername = document.querySelector(strFormId + ' input[name="username"]');
	var objUserID = document.querySelector(strFormId + ' input[name="userId"]');
	var objFirstPass = document.querySelector(strFormId + ' input[name="password"]');
	var listPass = document.querySelectorAll(strFormId + ' input[type="password"]');
	var objYear = document.querySelector(strFormId + ' select[name="year"]');
	var objMonth = document.querySelector(strFormId + ' select[name="month"]');
	var objDay = document.querySelector(strFormId + ' select[name="day"]');
	var objAlertBirthday = $('.alert-birthday');
	var objTerm = document.querySelector(strFormId + ' input[type="checkbox"]');
	var objAlertTerm = $('.alert-term');
	
	// User Name Check
	if (!checkInputLength(objUsername, 0, 10))
		isSuccess = false;
	
	// User ID Check
	if (!checkInputLength(objUserID, 4, 18))
		isSuccess = false;
    
	// Password Length Check
	if (!checkPasswordValid(strFormId, true))
		isSuccess = false;
	
	// Two Password Check
	if (!checkPasswordValid(strFormId))
		isSuccess = false;
	
	// Birthday Check
	if (objYear.value == "1900" && objMonth.value == "1" && objDay.value == "1")
	{
		setInputValid(objYear, false);
		setInputValid(objMonth, false);
		setInputValid(objDay, false);
		objAlertBirthday.show();
	}
	else
	{
		setInputValid(objYear, true);
		setInputValid(objMonth, true);
		setInputValid(objDay, true);
		objAlertBirthday.hide();
	}

	// Term Check
	if (objTerm.checked)
		objAlertTerm.hide();
	else if (isSuccess)
	{
		objAlertTerm.show();
		isSuccess = false;
	}

	// Finish
	return isSuccess;
}

function checkPasswordValid(strFormId, isValidCheck, isModify)
{
	// Field
	var intMin = 6;
	var intMax = 20;
	var objCurPass = document.querySelector(strFormId + ' .pass-cur');
	var objFirstPass = document.querySelector(strFormId + ' .pass-ori');
	var objSecondPass = document.querySelector(strFormId + ' .pass-valid');
	
	// Init
	isValidCheck = isValidCheck || false;
	isModify = isModify || false;
	
	// Process
	if (isValidCheck)
		return checkInputLength(objFirstPass, intMin, intMax);
	else
		if (checkInputLength(objFirstPass, intMin, intMax) && checkInputLength(objSecondPass, intMin, intMax))
			if (objFirstPass.value == objSecondPass.value)
				if (!isModify)
					return true;
				else
					if (objCurPass.value != objFirstPass.value)
						return true;
					else
						setInputValid(objFirstPass, false);
			else
				setInputValid(objSecondPass, false);
				
	
	// Default Return
	return false;
}

function checkMemberManage()
{
	// Field
	var objAlertBox = $('.alert-check-member');
	
	// Process
	if (document.querySelectorAll('input[name="intMemberId"]:checked').length <= 0)
	{
		objAlertBox.show();
		return false;
	}
	else
		objAlertBox.hide();
	
	if (!confirm("정말로 수행하시겠습니까?"))
		return false;
	return true;
}

function setTermRead(strCheckboxId)
{
	// Field
	var objCheckbox = document.getElementById(strCheckboxId);
	
	// Process
	objCheckbox.disabled = false;
}