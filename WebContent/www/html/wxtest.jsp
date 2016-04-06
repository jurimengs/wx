<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    
<meta name="apple-mobile-web-app-capable" content="yes" />    
<meta name="format-detection" content="telephone=no" />  
<title>微信api测试</title>
<%@ include file="/www/include/common.jsp"%>
<style type="text/css">
.wx_priview {
	max-width:320px;
}
</style>

<script type="text/javascript" charset="utf-8" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" charset="utf-8" src="/www/js/jquery-1.11.1.min.js"></script>

<script type="text/javascript">
//alert('${appId}' + ": " + '${signature }' + '   ${timestamp }' + '  ${nonceStr }' + '');
wx.config({
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
    appId: '${appId}', // 必填，公众号的唯一标识
    timestamp: '${timestamp }', // 必填，生成签名的时间戳
    nonceStr: '${nonceStr }', // 必填，生成签名的随机串
    signature: '${signature }',// 必填，签名，见附录1
    jsApiList: ['chooseImage', 'uploadImage', 'downloadImage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
});

wx.error(function(res){
	alert("error");
	var s = "";
	for (var k in res) {
		s += k + ":" + res[k];
	}
	alert(s);
});

function takePic(){
	wx.chooseImage({
	    count: 9, // 默认9
	    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
	    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
	    success: function (res) {
	        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
	        for(var i=0; i < localIds.length; i++){
	        	var idTemp = localIds[i];
	        	var imgStr = "<div><img class='wx_priview' src='"+idTemp+"' /></div>";
		        //var imgObj = getObjFromHtml(imgStr);
		        $("#wxPic").append(idTemp+"<br />");
		        $("#wxPic").append(imgStr);
	        }
	    }
	});
}

function getCacheToken(){
	window.location.href="/wx/getCacheToken.do";
}

function deleteBottomMenu(){
	window.location.href="/wx/deleteBottomMenu.do";
}

function initBottomMenu(){
	window.location.href="/wx/initBottomMenu.do";
}

function getGroupidList(){
	window.location.href="/wxUser/getGroupidList.do";
}

function getUserList(){
	window.location.href="/wxUser/getUserList.do";
}

function getUserGroup(){
	window.location.href="/wxUser/getUserGroup.do";
}

function getUserBaseInfo(){
	window.location.href="/wxUser/getUserBaseInfo.do";
}

</script>

</head>

<body>
<%-- <div>token: ${cacheToken }</div><br />

<div>signature: ${signature }</div><br />
<div>timestamp: ${timestamp } </div><br />
<div>nonceStr: ${nonceStr }</div><br /> --%>

<input onclick="getGroupidList();" type="button" value="getGroupidList"/>
<input onclick="getUserList();" type="button" value="getUserList"/>
<input onclick="deleteBottomMenu();" type="button" value="deleteBottomMenu"/>
<input onclick="initBottomMenu();" type="button" value="initBottomMenu"/>
<input onclick="getCacheToken();" type="button" value="getCacheToken"/>
<input onclick="takePic();" type="button" value="微信图片接口"/>
<div id="wxPic">
</div>
<span id="spanmsg"></span>
</body>
</html>
