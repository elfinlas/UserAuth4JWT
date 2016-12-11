

$(document).ready(function() {
	$('.tab a').click();
});


/////////////////////
//		Button
/////////////////////

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
        	if(resultMsg==='success'){
        		swal({
    				title: "가입성공",
    				text: "회원가입에 성공하였습니다.",
    				type: "success",
    				showCancelButton: false,
    				confirmButtonColor: '#449D44',
    				confirmButtonText: "확인",
    			}).then(function(isConfirm) {
    				if (isConfirm) {
    					location.reload(true); //화면 새로고침
    				}
    			});
        	}
        	else if(resultMsg==='existid'){
        		swal({
    				title: "가입실패",
    				text: "이미 존재하는 ID가 있습니다. ",
    				type: "error",
    				showCancelButton: false,
    				confirmButtonColor: '#C9302C',
    				confirmButtonText: "확인",
    			}).then(function(isConfirm) {
    				if (isConfirm) {
    					location.reload(true); //화면 새로고침
    				}
    			});
        	}
        },
        error: function(xhr, status, error){
        	swal({
				title: "가입실패",
				text: "회원가입에 실패하였습니다.",
				type: "error",
				showCancelButton: false,
				confirmButtonColor: '#C9302C',
				confirmButtonText: "확인",
			}).then(function(isConfirm) {
				if (isConfirm) {
					location.reload(true); //화면 새로고침
				}
			});
        }
    });
}



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
        	
        	 if(resultMsg==='success') {
        		swal({
    				title: "로그인 성공",
    				text: "로그인에 성공하였습니다. \n 게시판으로 이동해주세요.",
    				type: "success",
    				showCancelButton: false,
    				confirmButtonColor: '#449D44',
    				confirmButtonText: "확인",
    			}).then(function(isConfirm) {
    				if (isConfirm) {
    					self.location = "/userauth/board/main";
    				}
    			});
        	}
        	else if(resultMsg==='fail') {
        		swal({
    				title: "로그인 실패",
    				text: "로그인에 실패하였습니다.",
    				type: "error",
    				showCancelButton: false,
    				confirmButtonColor: '#C9302C',
    				confirmButtonText: "확인",
    			}).then(function(isConfirm) {
    				if (isConfirm) {
    					$('#userId').val("");
    					$('#userPw').val("");
    				}
    			});
        	}
        },
        error: function(xhr, status, error){
        	swal({
				title: "로그인 실패",
				text: "로그인에 실패하였습니다.",
				type: "error",
				showCancelButton: false,
				confirmButtonColor: '#C9302C',
				confirmButtonText: "확인",
			}).then(function(isConfirm) {
				if (isConfirm) {
					//location.reload(true); //화면 새로고침
				}
			});
        }
    });
}


function forgotPasswd() {
	swal({
		title: "암호 초기화",
		text: "관리자에게 문의하세요.",
		type: "error",
		showCancelButton: false,
		confirmButtonColor: '#C9302C',
		confirmButtonText: "확인",
	}).then(function(isConfirm) {
	});
}



/////////////////////
//		UI 
/////////////////////


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