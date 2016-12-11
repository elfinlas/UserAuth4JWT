<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Default Meta Data -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<!-- ETC -->
	<meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
	
	<!-- Title -->
	<title>Login Page</title>
	
	<!-- CSS  -->
	<!-- Framework And Lib -->
	<!-- Bootstrap 3.3.4 -->
	<link rel="stylesheet" type="text/css" href="/userauth/resources/repo/Bootstrap-3.3.7/css/bootstrap.min.css"/>
	
	<!-- Font-Awesome 4.7.0 -->
	<link rel="stylesheet" type="text/css" href="/userauth/resources/repo/font-awesome-4.7.0/css/font-awesome.min.css"/>

	<!-- Sweet-Alert 2.6.1.1 -->
	<link rel="stylesheet" type="text/css" href="/userauth/resources/repo/Sweetalert2_6.1.1/sweetalert2.min.css"/>
	
	
	<!-- MHLab -->
	<!-- Login -->
	<link rel="stylesheet" type="text/css" href="/userauth/resources/mhlab/css/ui/login/login.css"/>
	
</head>
<body>

	<div class="form">

		<ul class="tab-group">
			<li class="tab active"><a href="#signup">Sign Up</a></li>
			<li class="tab"><a href="#login">Log In</a></li>
		</ul>

		<div class="tab-content">
			<div id="signup">
				<h1>Sign Up</h1>

				<form name="signupForm">

					<div class="field-wrap">
						<label> Your ID<span class="req">*</span>
						</label> <input type="text" id="inputId"/>
					</div>

					<div class="field-wrap">
						<label> Your Password<span class="req">*</span>
						</label> <input type="password" id="inputPw"/>
					</div>

					<!--  
					<div class="top-row">
						<div class="field-wrap">
							<label> First Name<span class="req">*</span>
							</label> <input type="text" required autocomplete="off" />
						</div>

						<div class="field-wrap">
							<label> Last Name<span class="req">*</span>
							</label> <input type="text" required autocomplete="off" />
						</div>
					</div>

					<div class="field-wrap">
						<label> Email Address<span class="req">*</span>
						</label> <input type="email" required autocomplete="off" />
					</div>

					<div class="field-wrap">
						<label> Set A Password<span class="req">*</span>
						</label> <input type="password" required autocomplete="off" />
					</div>
					 -->

					<button type="button" class="button button-block" onclick="signupBt()">
					Get Started
					</button>

				</form>

			</div>

			<div id="login">
				<h1>User Auth 4 JWT</h1>

				<form name="loginForm">

					<div class="field-wrap">
						<label> User ID<span class="req">*</span>
						</label> <input type="text" id="userId"/>
					</div>

					<div class="field-wrap">
						<label> User Password<span class="req">*</span>
						</label> <input type="password" id="userPw" />
					</div>

					<p class="forgot">
						<a href="javascript:forgotPasswd()">Forgot Password?</a>
					</p>

					<button type="button" class="button button-block" onclick="loginBt()">
					Log In
					</button>

				</form>

			</div>

		</div>
		<!-- tab-content -->

	</div>
	<!-- /form -->


	<!-- Javascript -->
	<!-- Framework And Lib -->
	<!-- jQuery 2.1.4 -->
	<script src="/userauth/resources/repo/jQuery/jQuery-2.2.0.min.js" type="text/javascript"></script>
	
	<!-- jQuery-UI 1.11 -->
	<script src="/userauth/resources/repo/jQueryUI/jquery-ui.min.js" type="text/javascript"></script>

	<!-- Bootstrap 3.3.7 -->
	<script src="/userauth/resources/repo/Bootstrap-3.3.7/js/bootstrap.min.js" type="text/javascript"></script>

	<!-- Sweetalert2_6.1.1 -->
	<script src="/userauth/resources/repo/Sweetalert2_6.1.1/sweetalert2.min.js" type="text/javascript"></script>
	
	
	<!-- MHLab -->
	<!-- Login -->
	<script src="/userauth/resources/mhlab/js/ui/login/login.js" type="text/javascript"></script>	

</body>
</html>