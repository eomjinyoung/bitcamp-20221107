<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<meta http-equiv='Refresh' content='1;url=list'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>게시판(JSP + MVC2)</h1>

<c:if test="${empty error}">
  <p>입력 했습니다.</p>
</c:if>

<c:if test="${error == 'data'}">
  <p>입력 실패입니다!</p>
</c:if>

</body>
</html>

