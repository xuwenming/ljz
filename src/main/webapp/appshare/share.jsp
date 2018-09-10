<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>${title}</title>
<style type="text/css">
body {font-family:"微软雅黑";font-size:12px; background-color:#f8f7f5;}
body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,fieldset,input,textarea,blockquote,p{padding:0; margin:0;}
table,td,tr,th{font-size:12px;}
li{list-style-type:none; margin:0; padding:0;}
table{ margin:0 auto;}
img{border:0; line-height:0;}
ol,ul {list-style:none;}   
h1,h2,h3,h4,h5,h6 {font-size:12px; font-weight:normal;}   
a { color: #000; text-decoration: none; outline: none;}
a:visited {text-decoration:none;}
a:hover {text-decoration:none;}
a:active { }
a img { border: none; }
dd,dl,dt{ margin:0 ; padding:0;}

.content{ padding:5px 15px; margin:0 auto;}
.content h1{ display:block; line-height:1.8em; font-size:28px; color:#4c4c4c; font-weight:bold; text-align:left;}
.content h2{ display:block; font-size:12px; color:#999999; text-align:left;}
.content h2 span{ margin-right:4%;}
.content h2 a{ color:#1354b5;}
.content .nr{ margin-top:15px; overflow:hidden;}
.content .nr p{ font-size:14px; color:#666; line-height:1.8em; margin:.8em 0;}
.content .nr img{ width:100%;}

.down-app {
	/*height: 64px;*/
}
.down-app.down-bottom {
	position: fixed;
	width: 100%;
	bottom: 0;
	left: 0;
	z-index: 99;
}

.down-app-content {
	background: rgba(0,0,0,.3);
	margin: 0 auto;
	/* max-width: 1024px; */
}
.down-app-btn {
	float: right;
	padding: 20px;
}

.btn-danger {
	color: #fff;
	background-color: #d9534f;
	border-color: #d43f3a;
}

.btn {
	display: inline-block;
	padding: 8px 12px;
	margin-bottom: 0;
	font-size: 14px;
	font-weight: 400;
	line-height: 1.42857143;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	background-image: none;
	border: 1px solid transparent;
	border-radius: 4px;
}

.down-app-img img {
	/*height: 54px;*/
	padding: 10px 20px;
	vertical-align: middle;
}

</style>
<meta name="viewport" content="width=device-width, initial-scale=0.66, minimum-scale=0.66, maximum-scale=0.66, user-scalable=yes">
</head>
<body>
	<div class="content">
	 	<h1>${title}</h1>
	 	<h2><span><fmt:formatDate value="${date}" pattern="yyyy-MM-dd HH:mm:ss"/></span><a href="javascript:void(0);">${author } </a></h2>
	 	<div class="nr">
			${content }
	 	</div>
	</div>

	<div class="down-app-div">
		<div class="down-app down-bottom">
			<div class="down-app-content container">
				<div class="down-app-btn">
					<a href="javascript:void(0)" onclick="tryOpenApp()" class="btn btn-danger">立即打开</a>
				</div>
				<div class="down-app-img">
					<img src="${pageContext.request.contextPath}/appshare/60.png">
				</div>
			</div>
		</div>
	</div>

	<script>
		function tryOpenApp() {
			location.href= '${pageContext.request.contextPath}/appshare/download.jsp';
		}
	</script>
</body>
</html>