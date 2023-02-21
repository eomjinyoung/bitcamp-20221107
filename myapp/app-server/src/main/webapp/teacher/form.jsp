<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>강사(JSP)</h1>
<form action='insert.jsp' method='post'>
<table border='1'>
<tr>
  <th>이름</th>
  <td><input type='text' name='name'></td>
</tr>

<tr>
  <th>이메일</th>
  <td><input type='email' name='email'></td>
</tr>

<tr>
  <th>암호</th>
  <td><input type='password' name='password'></td>
</tr>

<tr>
  <th>전화</th>
  <td><input type='tel' name='tel'></td>
</tr>

<tr>
  <th>학위</th>
  <td><select name='degree'>
      <option value='1'>고졸</option>
      <option value='2'>전문학사</option>
      <option value='3'>학사</option>
      <option value='4'>석사</option>
      <option value='5'>박사</option>
      <option value='0'>기타</option>
      </select></td>
</tr>

<tr>
  <th>학교</th>
  <td><input type='text' name='school'></td>
</tr>

<tr>
  <th>전공</th>
  <td><input type='text' name='major'></td>
</tr>

<tr>
  <th>강의료(시급)</th>
  <td><input type='number' name='wage'></td>
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


