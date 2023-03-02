<%@page import="bitcamp.myapp.vo.Teacher"%>
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
<h1>강사(JSP + MVC2 + EL + JSTL)</h1>
<c:if test="${empty teacher}">
  <p>해당 번호의 강사가 없습니다.</p>
  <div>
    <button id='btn-list' type='button'>목록</button>
  </div>
</c:if>

<c:if test="${not empty teacher}">
  <form id='teacher-form' action='update' method='post'>

  <table border='1'>

  <tr>
    <th>번호</th>
    <td><input type='text' name='no' value='${teacher.no}' readonly></td>
  </tr>

  <tr>
    <th>이름</th>
    <td><input type='text' name='name' value='${teacher.name}'></td>
  </tr>

  <tr>
    <th>이메일</th>
    <td><input type='email' name='email' value='${teacher.email}'></td>
  </tr>

  <tr>
    <th>암호</th>
    <td><input type='password' name='password'></td>
  </tr>

  <tr>
    <th>전화</th>
    <td><input type='tel' name='tel' value='${teacher.tel}'></td>
  </tr>

  <tr>
    <th>학위</th>
    <td><select name='degree'>
        <option value='1' ${teacher.degree == 1 ? "selected" : ""}>고졸</option>
        <option value='2' ${teacher.degree == 2 ? "selected" : ""}>전문학사</option>
        <option value='3' ${teacher.degree == 3 ? "selected" : ""}>학사</option>
        <option value='4' ${teacher.degree == 4 ? "selected" : ""}>석사</option>
        <option value='5' ${teacher.degree == 5 ? "selected" : ""}>박사</option>
        <option value='0' ${teacher.degree == 0 ? "selected" : ""}>기타</option>
        </select></td>
  </tr>

  <tr>
    <th>학교</th>
    <td><input type='text' name='school' value='${teacher.school}'></td>
  </tr>

  <tr>
    <th>전공</th>
    <td><input type='text' name='major' value='${teacher.major}'></td>
  </tr>

  <tr>
    <th>강의료(시급)</th>
    <td><input type='number' name='wage' value='${teacher.wage}'></td>
  </tr>

  <tr>
    <th>등록일</th>
    <td>${teacher.createdDate}</td>
  </tr>

  </table>
	<div>
	  <button id='btn-list' type='button'>목록</button>
	  <button>변경</button>
	  <button id='btn-delete' type='button'>삭제</button>
	</div>
	
	</form>
</c:if>

<script>
document.querySelector('#btn-list').onclick = function() {
  location.href = 'list';
}

<c:if test="${not empty teacher}">
document.querySelector('#btn-delete').onclick = function() {
  var form = document.querySelector('#teacher-form');
  form.action = 'delete';
  form.submit();
}
</c:if>
</script>

</body>
</html>


