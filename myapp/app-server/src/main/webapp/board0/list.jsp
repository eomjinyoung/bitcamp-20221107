<%@ page import="bitcamp.myapp.vo.Board"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>게시판(JSP + MVC2 + EL + JSP 액션태그)</h1>

<div><a href='form'>새 글</a></div>

<table border='1'>
<tr>
  <th>번호</th> <th>제목</th> <th>작성일</th> <th>조회수</th>
</tr>
<jsp:useBean id="boards" scope="request" type="java.util.List<Board>"/>
<% 
    for (Board board : boards) {
      pageContext.setAttribute("b", board);
%>
  <tr>
     <td>${b.no}</td> 
     <td><a href='view?no=${b.no}'>${b.title}</a></td> 
     <td>${b.createdDate}</td> 
     <td>${b.viewCount} </td>
  </tr>
<% 
    }
%>

</table>

<form action='list' method='get'>
  <input type='text' name='keyword' value='${param.keyword}'>
  <button>검색</button>
</form>

</body>
</html>
















