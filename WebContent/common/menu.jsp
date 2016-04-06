<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
.addCommemorate {
	display: none; margin-left: 20px; margin-top: 5px; position: absolute; background: url(/images/addcommemorate.gif) no-repeat; width: 70px; height: 70px; text-align:center; padding-top:23px; cursor: pointer; font:normal 16px "Microsoft Yahei"; color:#fff;
}
#movmenu {
	display: none;
}
#movmenu div{
	width: 100%; font: normal 14px/24px solid; display: transparent;
}
.switchDivBtn {
}

#menudiv {
	display: none; background-color: #2e3138; color: #ccc; position:absolute; z-index: 103;
}

#menudiv div span{
	padding-left: 24px;
	width: 100%;
}

.topnav {
	color: #2e3136;
}
.topnav div span{
	padding-left: 15px;
}
/* 菜单的响应式 CSS*/
@media screen and (max-width: 600px) {
	#pcmenu { display: none; }
	#movmenu { display: block; }
}
</style>
<header style="">
	<div id="logo" class="fl">
		<a href="javascript:void(0);" onclick="turnToIndex();">
			人生<span>BAI</span>科 &nbsp;<span style="font: 12px/24px solid ">版本: 1.1</span>
		</a>
	</div>
	<nav id="pcmenu" class="fr">
		<div class="btn-gp fr">
			<c:if test="${empty sessionUser }">
				<a class="btn" href="/user/toLogin.do">登录</a>
				<a class="btn" href="/user/toRegist.do">注册</a>
			</c:if>
			<c:if test="${!empty sessionUser }">
				欢迎您：${sessionUser.loginName }
				<c:if test="${sessionUser.registType ne '1' }">
					<a class="btn" onclick="turnTo('/user/toRegist.do');" href="javascript:void(0);">完善资料</a>
				</c:if>
				<c:if test="${sessionUser.registType eq '1' }">
					<a class="btn" onclick="turnTo('/user/loginOut.do');" href="javascript:void(0);">退出</a>
				</c:if>
			</c:if>
		</div>
		<ul class="fr">
			<%-- <li <c:if test="${currentChannelId eq '5' }">class="currentChannel"</c:if>>
				<a href="javascript:void(0);" onmouseover="showAddCommemorate();" onclick="turnTo('/channel/commemorateBoard.do');">纪念板</a>
				<div id="addCommemorate" onclick="addCommemorate();" onmouseout="hideAddCommemorate();" class="addCommemorate">我要纪念</div>
			</li> --%>
			<%-- <li <c:if test="${currentChannelId eq '4' }">class="currentChannel"</c:if>>
				<a href="javascript:void(0);" onclick="turnToTuCaoBa();">吐槽吧</a>
			</li> --%>
			<li <c:if test="${currentChannelId eq '3' }">class="currentChannel"</c:if>>
				<a href="javascript:void(0);" onclick="turnToOther();">其他</a>
			</li>
			<li <c:if test="${currentChannelId eq '2' }">class="currentChannel"</c:if>>
				<a href="javascript:void(0);" onclick="turnToCareer();">职场</a>
			</li>
			<li <c:if test="${currentChannelId eq '1' }">class="currentChannel"</c:if>>
				<a href="javascript:void(0);" onclick="turnToEmotion();">情感</a>
			</li>
			<li <c:if test="${currentChannelId eq '0' }">class="currentChannel"</c:if>>
				<a href="javascript:void(0);" onclick="turnToLife();">生活</a>
			</li>
			<li <c:if test="${currentChannelId eq '6' }">class="currentChannel"</c:if>>
				<a href="javascript:void(0);" onclick="turnToIndex();">所有</a>
			</li>
			<li>
				<a href="/" onclick="">回首页</a>
			</li>
			<li>
				<a href="javascript:void(0);" onclick="openTestimonials('${currentChannelId}');">发表感言</a>
			</li>
		</ul>
	</nav>
	<div class="clear">
	</div>
</header>

<div id="movmenu">
	<div class="topnav">
		<div>
			<span>
			<c:if test="${empty sessionUser }">
				<a class="btn" href="javascript:void(0);">登录</a>
				<a class="btn" href="javascript:void(0);">注册</a>
			</c:if>
			<c:if test="${!empty sessionUser }">
				欢迎${sessionUser.loginName }
			</c:if>
			<a class="btn" href="javascript:void(0);">完善资料</a> &nbsp;
			</span>
		</div>
		<div><span><a onclick="switchMenu(this);" class="switchDivBtn">菜单</a></span></div>
	</div>
	<div id="menudiv">
		<div onclick="openTestimonials('${currentChannelId}');">
			<span>发表感言</span>
		</div>
		<div onclick="turnToIndex();" <c:if test="${currentChannelId eq '6' }">class="currentChannel"</c:if>>
			<span>首页</span>
		</div>
		<div onclick="turnToLife();" <c:if test="${currentChannelId eq '0' }">class="currentChannel"</c:if>>
			<span>生活</span>
		</div>
		<div onclick="turnToEmotion();" <c:if test="${currentChannelId eq '1' }">class="currentChannel"</c:if>>
			<span>情感</span>
		</div>
		<div onclick="turnToCareer();" <c:if test="${currentChannelId eq '2' }">class="currentChannel"</c:if>>
			<span>职场</span>
		</div>
		<div onclick="turnToOther();" <c:if test="${currentChannelId eq '3' }">class="currentChannel"</c:if>>
			<span>其他</span>
		</div>
		<div onclick="turnTo('/channel/commemorateBoard.do');"<c:if test="${currentChannelId eq '5' }">class="currentChannel"</c:if>>
			<span>纪念板</span>
		</div>
		
	</div>
</div>

<script type="text/javascript">
function switchMenu(obj){
	var disp = $("#menudiv").css("display");
	if(disp == "block"){
		$("#menudiv").slideUp();
		$(obj).text("菜单");
	} else {
		$("#menudiv").slideDown();
		$(obj).text("隐藏菜单");
	}
}
</script>
