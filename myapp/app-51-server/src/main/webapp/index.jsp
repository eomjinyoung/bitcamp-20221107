<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>강의관리시스템!</h1>
<ul>
  <li><a href="student/list">학생관리</a></li>
  <li><a href="teacher/list">강사관리</a></li>
  <li><a href="board/list">게시판</a></li>
  
<c:if test="${empty loginUser}">
  <li><a href="auth/form">로그인</a></li>
</c:if>

<c:if test="${not empty loginUser}">
  <li><a href="auth/logout">로그아웃(${loginUser.name})</a></li>
</c:if>

</ul>
</body>
</html>