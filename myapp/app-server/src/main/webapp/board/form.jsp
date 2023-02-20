<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>게시판(JSP)</h1>
<form action='insert.jsp' method='post'>
<table border='1'>
<tr>
  <th>제목</th>
  <td><input type='text' name='title'></td>
</tr>
<tr>
  <th>내용</th>
  <td><textarea name='content' rows='10' cols='60'></textarea></td>
</tr>
<tr>
  <th>암호</th>
  <td><input type='password' name='password'></td>
</tr>
</table>
<div>
  <button>등록</button>
  <button id='btn-cancel' type='button'>취소</button>
</div>
</form>

<script>
document.querySelector('#btn-cancel').onclick = function() {
  location.href = 'list.jsp';
}
</script>

</body>
</html>

