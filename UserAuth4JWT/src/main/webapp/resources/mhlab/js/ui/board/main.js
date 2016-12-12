/**
 * 
 */


function logoutBt() {
	swal({
		title: "로그아웃",
		text: "로그아웃을 하시겠습니까?",
		type: "question",
		showCancelButton: true,
		confirmButtonColor: "#3085d6",
		cancelButtonColor: '#d33',
		confirmButtonText: "확인",
		cancelButtonText: '취소',
	}).then(function(isConfirm) {
		$.ajax({
			url : '/userauth/login/logout',
	        type : 'POST',
	        data : "",
	        dataType : 'text',
	        success : function(resultMsg){
	        	if(resultMsg==='success'){
	        		showAlert("로그아웃 성공", "로그인 페이지로 돌아갑니다.", "success", "#449D44", 1)
	        	}
	        	else if(resultMsg==='fail'){
	        		showAlert("로그아웃 실패", "로그아웃에 실패하였습니다.", "error", "#C9302C", 2)
	        	}
	        },
	        error: function(xhr, status, error){
	        	showAlert("로그아웃 실패", "로그아웃에 실패하였습니다.(System)", "error", "#C9302C", 2)
	        }
	    });
	});
}


function moveLoginBt() {
	self.location="/userauth/login/login";
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
		if(callType===1){self.location="/userauth/login/login";}//로그인 페이지로 돌아간다.
	});
}
