<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="ie-comp">
<title>用户中心</title>
<link rel="stylesheet" type="text/css" href="/css/main.css">

<script src="/js/jquery.min.js" type="text/javascript"></script>
<script src="/js/common.js" type="text/javascript"></script>

<style type="text/css">
.rightmsg {
	width:100%;
	height:50px;
}
</style>
</head>

<body>
<div id="container" class="container">
	<div class="screen-height text-vertical-center ">
		<div class="cells" style="height:200px">
			<div class="cell-24">
				
			</div>
		</div>
		<div class="cells screen-height ">
			<div class="cell-6">&nbsp;</div>
			<div class="cell-10 bg-login-user" style="height:200px; padding:10px;">
			<div class="text-center" style="padding:10px;">
	      	用户名：${userInfo.loginName }
	      </div>
	      <div class="form-list">
	      	<label class="form-label t-right"></label>
	        <div class="form-content">
	        </div>
	      </div>
			</div>
		</div>
	</div>
	<form id="loginForm" method="post">
		<input type="hidden" class="notnull" id="realLoginName" name="loginName" />
		<input type="hidden" class="notnull" id="realLoginPwd" name="loginPwd" />
	</form>
</div>
</body>

<script type="text/javascript" >
//alert("");
$("#userLogin").click(function(){
	$("#realLoginName").val($("#username").val());
	$("#realLoginPwd").val($("#loginpwd").val());
	$("#loginForm").prop("action","/user/userLogin.do");

	var ids = ['username','loginpwd'];
	if(notNullArr(ids)){
		$("#loginForm").submit();
	}
});

$("#companyLogin").click(function(){
	$("#realLoginName").val($("#username-comp").val());
	$("#realLoginPwd").val($("#loginpwd-comp").val());
	$("#loginForm").attr("action","/user/companyLogin.do");

	var ids = ['username-comp','loginpwd-comp'];
	if(notNullArr(ids)){
		$("#loginForm").submit();
	}
});

</script>
</html>