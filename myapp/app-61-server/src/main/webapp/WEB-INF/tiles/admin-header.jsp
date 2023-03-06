<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<header>
강의관리시스템 - 관리자

  <a href="/web/admin/student/list">학생관리</a>
  <a href="/web/admin/teacher/list">강사관리</a>
  
<c:if test="${empty loginUser}">
  <a href="/web/app/auth/form">로그인</a>
</c:if>

<c:if test="${not empty loginUser}">
  <a href="/web/app/auth/logout">로그아웃(${loginUser.name})</a>
</c:if>
</header>