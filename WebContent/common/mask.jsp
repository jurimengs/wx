<%@ page contentType="text/html; charset=utf-8" %>
<style type="text/css">
/***modal***/

.message {
	display:none;
}
.message,.modal {
	background: rgba(0, 0, 0, 0.65);
	position: fixed;
	width: 100%;
	height: 100%;
	z-index: 10001;
	top: 0;
	left: 0;
}

.message .modal {
	background: none;
	top: inherit;
	left: inherit;
}

.message .modal,.modal-box,.message-box {
	width: 600px;
	margin: 8% auto 0 auto;
	border-radius: 4px;
	position: relative;
	overflow: hidden;
}

.modal .close:hover {
	text-decoration: none;
}

.modal-alert {
	width: 320px;
	font-size: 20px;
	margin: 12% auto 0 auto;
	background: #fff;
	border-radius: 2px;
	padding: 10px;
	text-align: center;
}

.modal-title,.modal-header,.message-box h3 {
	padding-left: 10px;
	font-size: 18px;
	line-height: 40px;
	background: #1a89bd;
	color: #fff;
	margin: 0;
}

.modal-content,.modal-body,.message-box .message-content {
	padding: 20px;
	background: #fff;
	border-bottom: 1px solid #ccc;
}

.modal-footer,.message-footer {
	padding: 10px;
	background: #f3f3f3;
	border-top: 1px solid #fff;
	border-bottom-left-radius: 4px;
	border-bottom-right-radius: 4px;
	text-align: center;
}

.modal-alert .modal-content {
	border: none;
}

.modal-show {
	visibility: hidden;
	position: absolute;
}

.modal-show+.modal {
	display: none;
}

.modal-show:checked+.modal {
	display: block;
}

.modal-box.dark,.modal-alert.dark {
	background: rgba(0, 0, 0, 0.65);
	color: #fff;
	border: 1px solid rgba(121, 157, 222, 0.2);
	box-shadow: 0px 0px 10px 0px rgba(0, 0, 0, 1)
}

.modal, .message{
	background:url(images/img/cover_65.png);
}
.close{
	right:5px;
	top:5px;
	width:24px;
	height:24px;
	line-height:18px;
	border-radius:20px;
	font-size:14px;
	font-weight:bold;
	background:#999999;
	cursor:pointer;
	border:none;
	color:#fff;
	text-align:center;
	padding:0;
	position:absolute;
	border:2px solid #fff;
}
.close:hover{
	background:#000000;
}
</style>

<div class="message" id="maskDiv">
	<div class="modal">
		<div class="modal-header" id="maskHead">
			<button id="maskClose" class="close" onclick="document.getElementById('maskDiv').style.display='none'; ">&times;</button>
			<div style="text-align:center;font-size:22px" id="maskTitle">&nbsp;</div>
		</div>
		<div class="modal-body" id="maskContent">&nbsp;
		</div>
	</div>
</div>
