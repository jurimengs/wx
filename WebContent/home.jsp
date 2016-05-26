<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%@ include file="/common/common.jsp"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>程序员百科 WEB程序员百科 webrsbk 提高你的程序员高度 工作频道 生活频道 纪念版 纪念 创造程序员价值 创造社会价值</title>
<meta name="description" content="程序员百科,WEB程序员百科,webrsbk,提高你的程序员高度,工作频道,生活频道,纪念版,创造程序员价值,创造社会价值">
<meta name="keywords" content="程序员百科">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">


<link rel="stylesheet" href="/css/simple.css">
<link rel="stylesheet" href="/css/mask.css">
<link rel="stylesheet" href="/css/pad.css" media="only screen and (max-width : 768px)">


<!-- <link rel="stylesheet" href="/css/base.css">
<link rel="stylesheet" href="/css/main.css">
<link rel="stylesheet" href="/css/mask.css">
<link rel="stylesheet" href="/css/pad.css" media="only screen and (max-width : 768px)">
 -->

<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/jquery.SuperSlide.2.1.1.js"></script>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
<script src="/js/html5shiv.min.js"></script>
<script src="/js/respond.min.js"></script>
<![endif]-->
<!--[if IE 6]>
<script type="text/javascript" src="/js/DD_belatedPNG.js"></script>
<script type="text/javascript">DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--修复IE6下PNG图片背景透明-->
<style type="text/css">
.commemorateDiv tr td {vertical-align: top; padding-left:20px;}
</style>
</head>

<body>
	<%-- <%@ include file="/common/menu-new.jsp"%> --%>
	<%@ include file="/common/menu-new.jsp"%>
	<div class="container">
		<div class="comwidth">
			<!-- <h1>
				感知生活<span>100%</span>了解您、<span>No.1</span>
				国内首页程序员BAI科平台、<span>1,000,000</span>位的访问者即将来临
			</h1> -->
			<section>
			<div class="h1style zhuti-bar">
			本站献给所有为梦想拼搏在一线的互联网人.
			</div>
			</section>
			<!-- section one -->
			<section class="first left">
				<c:forEach var="tms" items="${testimonialsArray }" varStatus="index">
					<%@ include file="/common/articleDiv.jsp"%><br />
				</c:forEach>
			</section>
			<div class="clear">
			</div>
			<%@ include file="/common/page.jsp"%> 
		</div>
	</div>
	<%@ include file="/common/footer.jsp"%> 
	<%@ include file="/common/floatblock.jsp"%>
</body>

<form id="channelForm" action="/channel/life.do" method="post" >
</form>
<c:if test="${! empty commemorate }">
<fmt:parseDate var="commemorateDateTemp" value="${commemorate.commemorateDate }" pattern="yyyyMMdd" />
<fmt:formatDate var="commemorateDate" value="${commemorateDateTemp }" type="both"/>

</c:if>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/business.js"></script>
<script type="text/javascript">
var time = 0;
$(".zhuti-bar").each(function(){
	var thisObj = $(this);
	time += 50;
	setTimeout(function(){
		thisObj.slideDown();
	}, time)
});

function createCommemorateDiv(){
	if(!! "${commemorateDateTemp}" && "null" != "${commemorateDateTemp}"){
		var maskContentDiv = $d("maskDiv").commonDialog();
		var imgHtml = "<table width='100%' class='commemorateDiv'>";
		imgHtml += "<tr>";
		imgHtml += "<td rowspan='3'><img style='max-height:450px;' src='${commemorate.filePath}'></td>";
		imgHtml += "<td>${commemorate.comments}</td>";
		imgHtml += "</tr>";
		// 底行
		imgHtml += "<tr>";
		imgHtml += "<td>";
		imgHtml += "被查看次数：${commemorate.viewTimes eq '' ? 0 : commemorate.viewTimes} 被顶次数：<span id='topTimes_home'>${commemorate.topTimes}</span>";
		imgHtml += "<br>";
		imgHtml += "${createDate}";
		imgHtml += "<br>";
		imgHtml += '<a href="javascript:void(0);" class="topOnce" onclick="topOnce(\'${commemorate.id }\', \'topTimes_home\');">顶一下</a>';
		imgHtml += "</td>";
		imgHtml += "</tr>";
		imgHtml += "</table>";
		var imgObj = getObjFromHtml(imgHtml);
		$d(maskContentDiv).append(imgObj);
	}
}

createCommemorateDiv();

</script>
<!-- <script type="text/javascript" src="/js/listener.js"></script> -->
</html>