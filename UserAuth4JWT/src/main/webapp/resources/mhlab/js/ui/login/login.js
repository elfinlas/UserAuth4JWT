

$(document).ready(function() {
	$('.tab a').click();
});


/////////////////////
//		Button
/////////////////////

//회원가입 처리 함수
function signupBt() {
	var inputId=$('#inputId').val();
	var inputPw=$('#inputPw').val();
	var jsonObj = {"inputId":inputId, "inputPw":inputPw};
	
	$.ajax({
		url : '/userauth/login/signup',
        type : 'POST',
        contentType: "application/json; charset=UTF-8",
        data : JSON.stringify(jsonObj),
        dataType : 'text',
        success : function(resultMsg){
        	if(resultMsg==='success'){showAlert("가입성공", "회원가입에 성공하였습니다.", "success", '#449D44', 1);}
        	else if(resultMsg==='existid'){showAlert("가입실패", "이미 존재하는 ID가 있습니다.", "error", '#C9302C', 2);}
        },
        error: function(xhr, status, error){
        	showAlert("가입실패", "회원가입에 실패하였습니다.", "error", '#C9302C', 3);
        }
    });
}

//로그인 처리 함수
function loginBt() {
	var inputId=$('#userId').val();
	var inputPw=$('#userPw').val();
	var jsonObj = {"inputId":inputId, "inputPw":inputPw};
	
	$.ajax({
		url : '/userauth/login/login',
        type : 'POST',
        contentType: "application/json; charset=UTF-8",
        data : JSON.stringify(jsonObj),
        dataType : 'text',
        success : function(resultMsg){
        	if(resultMsg==='success'){showAlert("로그인 성공", "로그인에 성공하였습니다. \n 게시판으로 이동합니다.", "success", '#449D44', 4);}
        	else if(resultMsg==='fail'){showAlert("로그인 실패", "로그인에 실패하였습니다.", "error", '#C9302C', 5);}
        },
        error: function(xhr, status, error){
        	showAlert("로그인 실패", "로그인에 실패하였습니다.(System)", "error", '#C9302C', 6);
        }
    });
}

//암호 잊은 경우
function forgotPasswd() {
	showAlert("암호 초기화", "관리자에게 문의하세요.", "error", '#C9302C', 7);
}



/////////////////////
//		UI 
/////////////////////

function showAlert(titleMsg, txtMsg, alertType, colorStr, callType) {
	swal({
		title: titleMsg,
		text: txtMsg,
		type: alertType,
		showCancelButton: false,
		confirmButtonColor: colorStr,
		confirmButtonText: "확인",
	}).then(function(isConfirm) {
		if(callType===1 ||callType===2 | callType===3){location.reload(true);}//회원가입 성공, 실패, 에러
		else if (callType===4){self.location="/userauth/board/main";}
		else if (callType===5) {
			$('#userId').val("");
			$('#userPw').val("");
		}
	});
}


$('.form').find('input, textarea').on('keyup blur focus', function(e) {
	var $this = $(this),
	label = $this.prev('label');

	if (e.type === 'keyup') {
		if ($this.val() === '') {
			label.removeClass('active highlight');
		} else {
			label.addClass('active highlight');
		}
	} else if (e.type === 'blur') {
		if ($this.val() === '') {
			label.removeClass('active highlight');
		} else {
			label.removeClass('highlight');
		}
	} else if (e.type === 'focus') {

		if ($this.val() === '') {
			label.removeClass('highlight');
		} else if ($this.val() !== '') {
			label.addClass('highlight');
		}
	}

});

$('.tab a').on('click', function(e) {

	e.preventDefault();

	$(this).parent().addClass('active');
	$(this).parent().siblings().removeClass('active');

	target = $(this).attr('href');

	$('.tab-content > div').not(target).hide();

	$(target).fadeIn(600);

});