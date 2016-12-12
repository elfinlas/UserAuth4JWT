# Introduction


## What is UserAuth4JWT Project?
<br>
  
**JWT를 이용한 사용자 인증을 처리하는 것을 샘플로 구현한 프로젝트입니다.**  
**업데이트는 지속적으로 진행하겠지만 Sample 프로젝트이기 때문에 어느정도 완성될 경우 종료될 예정입니다.**  
**최초 업데이트는 2016년 12월 12일에 이루어졌습니다.**

<br><br><br>


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

## Database
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
