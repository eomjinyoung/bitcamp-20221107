<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>학생(JSP + MVC2 + EL + JSTL)</h1>

<div><a href='form'>새 학생</a></div>

<table border='1'>
<tr>
  <th>번호</th> <th>이름</th> <th>전화</th> <th>재직</th> <th>전공</th>
</tr>
<c:forEach items="${students}" var="student">
  <tr>
      <td>${student.no}</td> 
      <td><a href='view?no=${student.no}'>${student.name}</a></td> 
      <td>${student.tel}</td> 
      <td>${student.working ? "예" : "아니오"}</td> 
      <td>
        <c:choose>
          <c:when test="${student.level == 0}">비전공자</c:when>
          <c:when test="${student.level == 1}">준전공자</c:when>
          <c:when test="${student.level == 2}">전공자</c:when>
          <c:otherwise>기타</c:otherwise>
        </c:choose>
      </td>
  </tr>
</c:forEach>

</table>

<form action='list' method='get'>
  <input type='text' name='keyword' value='${param.keyword}'>
  <button>검색</button>
</form>

</body>
</html>


