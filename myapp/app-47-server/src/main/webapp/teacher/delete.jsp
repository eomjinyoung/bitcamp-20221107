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
<h1>강사(JSP + MVC2)</h1>
<% 
    String error = (String) request.getAttribute("error");

    if (error == null) {
%>
      <p>삭제했습니다.</p>
<% 
    } else if (error.equals("data")) {
%>
      <p>해당 번호의 강사가 없습니다.</p>
<% 
    } else {
%>
      <p>삭제 실패입니다.</p>
<%      
    }
%>
</body>
</html>




