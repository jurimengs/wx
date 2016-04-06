<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />    
<meta name="format-detection" content="telephone=no" />  
<title>管理货架</title>
<%@ include file="/common/common.jsp"  %>
<link href="/www/css/shelf.css?v=<%=b %>" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/www/cordova.js?v=<%=b %>"></script>
<script type="text/javascript" charset="utf-8" src="/www/js/index.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/common.js?v=<%=b %>"></script>

<script type="text/javascript" charset="utf-8">
    function loadImage() {
    	try{
	        //拍照并显示在屏幕
	        navigator.camera.getPicture(onLoadImageSuccess, onLoadImageFail, {destinationType: Camera.DestinationType.DATA_URL});
    	}catch(e) {
    		$("#errmsg").val(e);
    	}
    }
    //拍照成功后回调
    function onLoadImageSuccess(imageURI) {
    	try{

        	// 调试慎用.以防字符过长手机端卡死
            //navigator.notification.alert(imageURI, null, "警告");
            //这里的图片经过了base64编码
            var src = "data:image/jpeg;base64," + imageURI;
            $("#getImage").attr("src", src);
            $("#getImage").show();
    	}catch(e) {
    		$("#errmsg").val(e);
    	}
    }
    //所有获取图片失败都回调此函数
    function onLoadImageFail(message) {
    	/* try{
	            navigator.notification.alert("" + message, null, "警告");
    	}catch(e) {
    		$("#errmsg").val(e);
    	} */
    }
    function loadImageLocal() {
    	try{

            //获取本地图片并显示在屏幕
            navigator.camera.getPicture(onLoadImageLocalSuccess, onLoadImageFail, {
                destinationType: Camera.DestinationType.FILE_URI,
                sourceType: Camera.PictureSourceType.PHOTOLIBRARY,
                                         // 剪裁后的图片不知道在哪里
                quality: 50,
                sourceType: 0,
                allowEdit: true, // 剪裁框
                targetWidth: 1000, 
                targetHeight: 1000
            });
    	}catch(e) {
    		$("#errmsg").val(e);
    	}
    }
    //本地图片选择成功后回调此函数
    function onLoadImageLocalSuccess(imageURI) {
    	try{
    		var srcPath = imageURI.substring(0, imageURI.indexOf('?'));
    		onLoadImageUploadSuccess(srcPath);
    	}catch(e) {
    		$("#errmsg").val(e);
    	}
    }
    function loadImageUpload() {
    	try{

            //拍照上传并显示在屏幕
            navigator.camera.getPicture(onLoadImageUploadSuccess, onLoadImageFail, {
                destinationType: Camera.DestinationType.FILE_URI,
                quality: 50,
                sourceType: 1,
                allowEdit: true, // 剪裁框
                targetWidth: 1000, 
                targetHeight: 1000
            });
    	}catch(e) {
    		$("#errmsg").val(e);
    	}
    }
    //图片拍照成功后回调此函数
    function onLoadImageUploadSuccess(imageURI) {
    	//alert(imageURI);
    	try{

            //此处执行文件上传的操作，上传成功后执行下面代码
            var options = new FileUploadOptions(); //文件参数选项
            options.fileKey = "file";//向服务端传递的file参数的parameter name
            options.fileName = imageURI.substr(imageURI.lastIndexOf('/') + 1);//文件名
            options.mimeType = "image/jpeg";//文件格式，默认为image/jpeg
            var ft = new FileTransfer();//文件上传类
            ft.onprogress = function (progressEvt) {//显示上传进度条
                if (progressEvt.lengthComputable) {
                    navigator.notification.progressValue(Math.round(( progressEvt.loaded / progressEvt.total ) * 100));
                }
            };
            
            var src = imageURI;
            // TODO 这个进度提示应该弄成弹出层
            navigator.notification.progressStart("提醒", "当前上传进度");
            ft.upload(imageURI, encodeURI('http://192.168.22.171:8880/upload.do'), function () {
            	try {
            		var startLocation = imageURI.lastIndexOf("/")+1;
                	var fileName = imageURI.substring(startLocation, imageURI.length);
                    navigator.notification.progressStop();//停止进度条
                    var forSavePath = "/files/" + getYYYYMMDD()+"/"+fileName;
                    // v 是为了消除缓存
                    var serverFileName = "http://192.168.22.171:8880"+ forSavePath +"?v="+getCurrentTimeMillis();
                    $("#exampPic").attr("src", serverFileName);
                    // 这个值是要保存到数据库的
                    $("#picPath").val(forSavePath);
                    navigator.notification.alert("文件上传成功！", null, "提醒");
            	} catch(e) {
            		alert(e);
            	}
            }, null, options);
    	}catch(e) {
    		$("#errmsg").val(e);
    	}
    }
    
    function setValTo(aimId, v){
    	$("#"+aimId).val(v);
    }
</script>

</head>

<body>
<ul>
	<li class="head clear" >
        <div onclick="pageComponent.back();" class="top_module_left flo_left">
	        <a href="javascript:void(0);">返回</a>
        </div>
        <div class="top_module_f">修改商品信息</div>
        <!--<div class="top_module_right"><a href="#">管理</a></div>-->
    </li>
    <li class="main_mod clear">
        <!--modify-->
        <ul class="mod_public_auto_h flo_left">
        	<li class="mod_line">商品名称<span class="clo_gray flo_right"><input class="short_input" onkeyup="setValTo('goodsName', this.value)" type="text" value="${goods.goodsName}" ></span></li>
        	<li class="mod_line">商品价格<span class="clo_gray flo_right"><input class="short_input" onkeyup="setValTo('goodsPrice', this.value)" type="text" value="${goods.goodsPrice}" ></span></li>
            <li class="mod_line">数量<span class="clo_gray flo_right"><input class="short_input" onkeyup="setValTo('goodsCounts', this.value)" type="text" value="${goods.goodsCounts}" ></span></li>
            <li class="mod_pic">
              <div class="flo_left">
              	<img id="exampPic" src="${goods.picPath}" onclick="alert(this.src);" width="150" height="150">
              </div> 
              <div class="flo_left">
              	<input onclick="loadImageLocal();" class="btn_local_pic" type="button" value="本地图片">
              </div>
              <div class="flo_right" style="margin-top:12px;cursor:pointer;">
              	<img onclick="loadImageUpload();" src="/www/images/photo.png" width="43" height="35">
              </div>
            </li>
        </ul><!--modify over-->
    </li>
    <li class="footer clear">
    	<div class="flo_left" style="margin-left:10px;">
    		<input name="" onclick="history.back();" class="btn_public btn_cancel" type="button" value="取消">
    	</div>
    	<div class="flo_right" style="margin-right:10px;">
    		<form action="/goods/save.do" method="post">
               	<input type="hidden" id="id" name="id" value="${goods.id}"> 
               	<input type="hidden" id="goodsName" name="goodsName" value="${goods.goodsName}"> 
               	<input type="hidden" id="picPath" name="picPath" value="${goods.picPath}"> 
               	<input type="hidden" id="goodsCounts" name="goodsCounts" value="${goods.goodsCounts}"> 
               	<input type="hidden" id="goodsPrice" name="goodsPrice" value="${goods.goodsPrice}">
               	<input name="" class="btn_public btn_sunmit" type="submit" value="提交"> 
            </form>
    	</div>
    </li>
</ul>

</body>
</html>
