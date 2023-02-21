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
<h1>게시판(JSP + MVC2 + EL + JSTL)</h1>

<c:if test="${empty board}">
  <p>해당 번호의 게시글 없습니다.</p>
  <div>
    <button id='btn-list' type='button'>목록</button>
  </div>
</c:if>

<c:if test="${not empty board}">
  <form id='board-form' action='update' method='post'>
  <table border='1'>
  <tr>
    <th>번호</th>
    <td><input type='text' name='no' value='${board.no}' readonly></td>
  </tr>
  <tr>
    <th>제목</th>
    <td><input type='text' name='title' value='${board.title}'></td>
  </tr>
  <tr>
    <th>내용</th>
    <td><textarea name='content' rows='10' cols='60'>${board.content}</textarea></td>
  </tr>
  <tr>
    <th>암호</th>
    <td><input type='password' name='password'></td>
  </tr>
  <tr>
    <th>등록일</th>
    <td>${board.createdDate}</td>
  </tr>
  <tr>
    <th>조회수</th>
    <td>${board.viewCount}</td>
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

<c:if test="${not empty board}">
document.querySelector('#btn-delete').onclick = function() {
  var form = document.querySelector('#board-form');
  form.action = 'delete';
  form.submit();
}
</c:if>
</script>

</body>
</html>

