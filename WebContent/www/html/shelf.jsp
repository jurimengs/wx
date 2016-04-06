<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

    
<meta name="apple-mobile-web-app-capable" content="yes" />    
<meta name="format-detection" content="telephone=no" />  
<title>我的货架</title>
<%@ include file="/common/common.jsp"%>
<link href="/www/css/shelf.css?v=<%=b %>" rel="stylesheet" type="text/css">
<script type="text/javascript" charset="utf-8" src="/www/cordova.js?v=<%=b %>"></script>
<script type="text/javascript" charset="utf-8" src="/www/js/index.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/js/common.js?v=<%=b %>"></script>


<script type="text/javascript" charset="utf-8">

function myTest(){
	var args = {};
	args.goodsPrice = "";
	var param = {};
	param.args = args;
	// TODO
	param.activityName = "com.test.MainActivity";
	navigator.myplugin.callActivity(function(){
		alert("success");
	}, function(){
		alert("error");
	}, [param]);
}

</script>

</head>

<body>
<ul>
	<li class="head clear">
        <div class="top_module_left flo_left"><a href="#">返回</a></div>
        <div class="top_module_f"><a href="javascript:void(0);" onclick="">货架管理</a></div>
        <div class="top_module_right">
        	<a href="javascript:void(0);" onclick="pageComponent.showHide('menuDiv')">操作</a>
        </div>
        <div id="menuDiv" class="drop_down">
        	<ul>
            	<li onclick="pageComponent.multiChoose('delete');" class="drop_text"><a href="#">删除</a></li> 
                <li onclick="pageComponent.multiChoose('recieveMoney');" class="drop_text"><a href="#">收款</a></li> 
                <li onclick="pageComponent.multiChoose('upToShelf');" class="drop_text"><a href="#">上架</a></li> 
                <li onclick="pageComponent.multiChoose('downFromShelf');" class="drop_text"><a href="#">下架</a></li> 
                <li onclick="pageComponent.multiChoose('edit');" class="drop_text"><a href="#">编辑</a></li> 
            </ul>
        </div>
    </li>
    <li class="main_mod clear">
    	<!--mod1-->
    	<c:forEach var="goods" items="${goodsArr }" varStatus="ci">
	    	<ul class="mod_public  flo_left">
	        	<li class="good_li marg_rig_6" onclick="pageComponent.goodsDivClick('/goods/edit.do?goodsId=${goods.id}', this)">
	            	<div class="pos_rel flo_left" >
	                	<div class="pos_obso inp_squre_sel"></div>
	                	<div class="squre_avter"><img src="${goods.picPath}" width="54" height="54"></div>
	                </div>
	                <div class="pos_rel flo_right descrip_rig">
	                	<h5>${goods.goodsName}</h5>
	                    <font><span class="fen">￥</span><span class="goodsPrice">${goods.goodsPrice}</span><span class="fen"></span></font>
	                </div>
	                <div class="amount">
	                	<div class="flo_left"><a href="#"><img src="../images/plus_duck.png" width="15" height="15" ></a></div>
	                    <div class="amo_f">${goods.goodsCounts}</div>
	                    <div class="amo_right"><a href="#"><img src="../images/minus_gray.png" width="15" height="15"></a></div>
	                </div>
	            </li>
	        </ul><!--mod1 over-->
        </c:forEach>
    </li>
    <li class="footer clear" id="footerLi" >
    	<div class="flo_left" style="margin-left:10px;"><input onclick="pageComponent.checkAll('good_li');" name="" type="checkbox" value="全选">全选 </div>
    	<div id="recieveMoney" class="flo_right" style="margin-right:10px;">
    		<input id="totalPrice" class="short_input" name="" type="text">
    		<input name="" class="btn_public btn_collect" type="button" onclick="myTest();" value="收款">
    	</div>
    	<div id="deleteBtn" class="flo_right" style="margin-right:10px;">
    		<input name="" class="btn_public btn_collect"type="button" value="删除">
    	</div>
    	<div id="upToShelf" class="flo_right" style="margin-right:10px;">
    		<input name="" class="btn_public btn_collect"type="button" value="上架">
    	</div>
    	<div id="downFromShelf" class="flo_right" style="margin-right:10px;">
    		<input name="" class="btn_public btn_collect"type="button" value="下架">
    	</div>
    </li>
</ul>
<a href="javascript:void(0);" onclick="myTest()">myTest</a>
</body>
<script type="text/javascript">
</script>
</html>
