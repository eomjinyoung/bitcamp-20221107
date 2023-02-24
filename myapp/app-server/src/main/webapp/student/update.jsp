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
<h1>학생(JSP + MVC2 + EL + JSTL)</h1>
<c:choose>
  <c:when test="${empty error}">
      <p>변경했습니다.</p>
  </c:when>
  <c:otherwise>
      <p>변경 실패입니다.</p>
  </c:otherwise>  
</c:choose>
</body>
</html>


