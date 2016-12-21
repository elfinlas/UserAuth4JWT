# Introduction


## What is UserAuth4JWT Project?

**JWT를 이용한 사용자 인증을 처리하는 것을 샘플로 구현한 프로젝트입니다.**  
**업데이트는 지속적으로 진행하겠지만 Sample 프로젝트이기 때문에 어느정도 완성될 경우 종료될 예정입니다.**  
**최초 업데이트는 2016년 12월 12일에 이루어졌습니다.**  
**2016년 12월 19일에 완료되었습니다.**  
**자세한 것은 [블로그 참조](https://elfinlas.github.io/views/blog/dev/mp/post/201612/1213_02_userauth.html) 를 참고해주세요.**

<br>

## 업데이트 이력 
* 16. 12. 12
	* 프로젝트 초기 업데이트 
* 16. 12. 13
	* 소스코드 정리 완료 및 1차 개발 완료
* 16. 12. 19
	* ReadMe 업데이트 및 프로젝트 종료
* 16. 12. 22
	* 프로젝트 정리 문서 블로그 링크 업데이트

<br>

## How to use?

1. 필요한 데이터베이스 생성 
	* 문서 내 <b>Database Table</b>를 참고하세요.
2. 스프링의 servlet-context.xml 등에서 token 정보가 저장될 경로를 bean으로 등록합니다.
	* 물론 이 과정은 해당 경로를 다른 식으로 구현할 경우 하지 않아도 됩니다.
	* 문서 내 <b>Bean 설정</b>를 참고하세요.
3. 프로젝트의 설정을 마친 후 서버를 구동하여 다음의 주소로 이동합니다.
	* localhost:8080/userauth/login/login
	* 다른 url 이동 시 페이지가 없는 등의 예외는 구현하지 않았습니다.
	* ![](https://github.com/elfinlas/UserAuth4JWT/blob/master/img/login01.png?raw=true)
4. Sign Up으로 이동하여 테스트에 사용할 사용자 id와 암호를 입력 후 <b>GET STARTED</b> 버튼을 눌러 등록합니다.
5. 테스트에 사용할 사용자 ID와 암호를 입력 후 <b>LOGIN</b> 버튼을 눌러 등록합니다.
	* 샘플 페이지라 공백 체크 및 기타 검증 로직은 미구현 상태입니다.
	* ![](https://github.com/elfinlas/UserAuth4JWT/blob/master/img/login02.png?raw=true)
6. JWT의 토큰 정보가 뜨는 페이지로 이동하게 됩니다.
	* 토큰의 유효시간은 임시로 1분을 주었으며 토큰 생성 정보는 com.mhlab.userauth.service.SecretServiceImpl.java 파일에서 확인 가능합니다.
	* ![](https://github.com/elfinlas/UserAuth4JWT/blob/master/img/jwt01.png?raw=true)
7. 토큰의 만료시간이 지나면 다음과 같이 창이 뜹니다.
	* ![](https://github.com/elfinlas/UserAuth4JWT/blob/master/img/jtw02.png?raw=true)
8. 만약 로그인하지 않은 상태에서 localhost:8080/userauth/board/main 으로 이동 시 다음과 같이 뜹니다.
	* ![](https://github.com/elfinlas/UserAuth4JWT/blob/master/img/nologin.png?raw=true)
	
<br>

## JWT Valid

1. [JWT](https://jwt.io) 페이지에서 Debugger로 가보면 Encoded쪽에 토큰 정보를 입력하는 곳이 있습니다.
2. 이곳에 자신이 만든 JTW 토큰 정보를 넣게 되면 아래의 사진처럼 하단에 <b>Invalid Signature</b> 가 뜹니다.
	* * ![](https://github.com/elfinlas/UserAuth4JWT/blob/master/img/jwt_valid01.png?raw=true)
3. 서명값을 입력해줘야 하는데 프로젝트에서 토큰 정보가 저장될 경로를 지정한 곳에 가보면 사용자 id 디렉토리 안에 <b>token_key</b> 파일이 있습니다.
4. 저 파일을 열어보면 \`Key tokenKey = MacProvider.generateKey(SignatureAlgorithm.HS256); //토큰의 서명 알고리즘`\ 를 Base64를 이용하여 String으로 인코딩을 한 값이 있습니다.
	* ![](https://github.com/elfinlas/UserAuth4JWT/blob/master/img/tokenkey.png?raw=true)
5. 해당 내용을 사이트 하단의 <b>VERIFY SIGNATURE</b>에 넣고 secret base64 항목에 체크를 하시면 아래의 사진처럼 검증이 완료됩니다.
	* ![](https://github.com/elfinlas/UserAuth4JWT/blob/master/img/jwt_valid02.png?raw=true)

<br>
	
## Database Table
* DB : userauth
* Table info 
``` sql
CREATE TABLE `tbl_account_data` (
  `ta_idx` int(10) NOT NULL AUTO_INCREMENT,
  `ta_id` varchar(60) COLLATE utf8_unicode_ci NOT NULL,
  `ta_pw` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  `ta_create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ta_secret_key` varchar(300) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ta_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
```

<br>
	
## Bean 설정
``` xml
<beans:bean id="baseSecretPath" class="java.lang.String">
  <beans:constructor-arg value="User-Path"></beans:constructor-arg>
</beans:bean>
```	

<br>

## Development Environment

* Oracle Java 1.8
* Tomcat 8.0.28
* Mysql 5.5.49
* Mac OS 10.12
* Spring tool suite 3.8.1.RELEASE

<br>

## 사용한 OpenSource 및 기술

* 로그인 화면 <http://codepen.io/ehermanson/pen/KwKWEv>
* Bootstrap 3.3.6
* Font-Awesome-4.7.0
* JQuery 2.2.0
* JQuery UI 1.11.4
* SweetAlert2 6.1.1
* Java 1.8
* Spring Framework 4.2.6
* JWT io.jsonwebtoken <https://github.com/jwtk/jjwt>
* https://jwt.io
