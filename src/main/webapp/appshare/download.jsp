<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.mobian.listener.Application" %>
<%
	String sourse = request.getParameter("sourse");
	String iosDownloadUrl, androidDownloadUrl;
	if(sourse == null || "1".equals(sourse)) {
		iosDownloadUrl = Application.getDescString("APP01");
		androidDownloadUrl = Application.getDescString("APP02");
	} else {
		iosDownloadUrl = Application.getDescString("APP03");
		androidDownloadUrl = Application.getDescString("APP04");
	}

%>
<!DOCTYPE html>
<html>
<head>
<title>医家盟</title>
<meta name="viewport" content="width=device-width, initial-scale=0.66, minimum-scale=0.66, maximum-scale=0.66, user-scalable=yes">
<script type="text/javascript">
	function download() {
		switch(getDevice()) {
			case 'Android':
				window.location.href="<%=androidDownloadUrl %>";
				break;
			case 'iOS':
				window.location.href="<%=iosDownloadUrl %>";
				break;
			default:
				window.location.href="<%=androidDownloadUrl %>";
				break;
		}
	}
	
	function isWeiXin() {   
	    var ua = window.navigator.userAgent.toLowerCase();  
	    if(ua.match(/MicroMessenger/i) == 'micromessenger') {   
	        return true;  
	    } else {   
	        return false;  
	    }   
	}  
	  
	function getDevice() {  
	    var u = navigator.userAgent;  
	    if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) {  
	        return 'Android';  
	    } else if (u.indexOf('iPhone') > -1) {  
	        return 'iOS';  
	    } else {  
	        return 'none';  
	    }  
	}  
</script>
</head>
<body onload="download();">
</body>
</html>