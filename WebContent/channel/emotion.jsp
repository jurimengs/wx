<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<%@ include file="/common/common.jsp"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>${channerName }</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<link rel="stylesheet" href="/css/base.css">
<link rel="stylesheet" href="/css/main.css">
<link rel="stylesheet" href="/css/mask.css">
<link rel="stylesheet" href="/css/pad.css" media="only screen and (max-width : 768px)">

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
</head>

<body>
	<%@ include file="/common/menu.jsp"%>
	<div id="banner">
		<div id="slideBox" class="slideBox">
			<div class="bd">
				<ul>
					<c:set var="bannerArray" value="${fn:split('images/banner-emotion.jpg', ',') }" />
					<c:forEach var="banner" items="${bannerArray }">
						<li>
							<a href="javascript:void(0);">
								<img src="/${banner}">
							</a>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="comwidth">
			<div class="h1style zhuti-bar">
				你是否有过让你<span>抓狂</span>到无法淡定的经历？ 你是否面对自己的<span>女神</span>无法HOLD住? 快来看看大家是怎么<span>解决</span>的吧！
				<br />
				<div style="text-align:left;"><a style=" color:#ccc;" href="javascript:void(0);"></a></div>
			</div>
			<!-- section one -->
			<section class="first left">
				<c:forEach var="tms" items="${testimonialsArray }" varStatus="index">
					<%@ include file="/common/articleDiv.jsp"%><br />
				</c:forEach>
			</section>
			<div class="clear">
			</div>
		</div>
	</div>
	<%@ include file="/common/footer.jsp"%> 
	<%@ include file="/common/floatblock.jsp"%> 
</body>

<form id="channelForm" action="/channel/life.do">
</form>

<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/business.js"></script>
<!-- <script type="text/javascript" src="/js/listener.js"></script> -->
</html>