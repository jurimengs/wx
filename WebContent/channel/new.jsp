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

<link rel="stylesheet" href="/css/simple.css">
<link rel="stylesheet" href="/css/mask.css">
<link rel="stylesheet" href="/css/pad.css" media="only screen and (max-width : 768px)">

<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="/kindeditor/plugins/code/prettify.js"></script>

<script>
	KindEditor.ready(function(K) {
		var editor1 = K.create('textarea[name="contents"]', {
			cssPath : '/css/kindeditor/prettify.css',
			//uploadJson : '../jsp/upload_json.jsp',
			//fileManagerJson : '../jsp/file_manager_json.jsp',
			allowFileManager : true,
			afterCreate : function() {
				var self = this;
				K.ctrl(document, 13, function() {
					self.sync();
					document.forms['commentsForm'].submit();
				});
				K.ctrl(self.edit.doc, 13, function() {
					self.sync();
					document.forms['commentsForm'].submit();
				});
			}
		});
		prettyPrint();
	});
</script>
	
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
.div_new{margin: 152px 0 0 152px; height: 800px; width: 80%; }
</style>
</head>

<body>
	<div class="container">
		<%@ include file="/common/menu-new.jsp"%>
		<div class="comwidth div_new" style="margin-top: 0px;">
			<form method="post" id="commentsForm" action="/testimonials/saveContentsNoPic.do">
				<div style="display:none;">
					<input placeholder="主题" name="testimonialsTitle" id="testimonialsTitle" class="input-small commentsTitle">
				</div>
				<br />
				<div>
					<div>选择频道</div>
					<select name="channelId" id="channelId">
						<option value="0">生活频道</option>
						<option value="1">情感频道</option>
						<option value="2">工作频道</option>
						<option value="3">其他频道</option>
					</select>
				</div>
				<br />
				<div>
					<textarea name="contents" style="width:100%; visibility:hidden;" placeholder="内容" id="testimonialsContent" ></textarea>
					<input type="submit" name="button" value="提交内容" /> (提交快捷键: Ctrl + Enter)
				</div>
			</form>
		</div>
	</div>
	<%-- <%@ include file="/common/footer.jsp"%>  --%>
</body>

<form id="channelForm" action="/channel/life.do">
</form>

<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/business.js"></script>
<!-- <script type="text/javascript" src="/js/listener.js"></script> -->
</html>