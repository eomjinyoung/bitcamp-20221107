<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<meta http-equiv='Refresh' content='1;url=list'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>학생(JSP + MVC2)</h1>
<% 
    String error = (String) request.getAttribute("error");

    if (error == null) {
%>
      <p>입력했습니다.</p>
<% 
    } else {
%>
      <p>입력 실패입니다.</p>
<%      
    }
%>
</body>
</html>


