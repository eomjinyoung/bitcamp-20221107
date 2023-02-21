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
<h1>게시판(JSP + MVC2)</h1>

<div><a href='form'>새 글</a></div>

<table border='1'>
<tr>
  <th>번호</th> <th>제목</th> <th>작성일</th> <th>조회수</th>
</tr>
<% 
    String keyword = request.getParameter("keyword");
    List<Board> boards = (List<Board>) request.getAttribute("boards");
    for (Board b : boards) {
%>
  <tr>
     <td><%=b.getNo()%></td> 
     <td><a href='view?no=<%=b.getNo()%>'><%=b.getTitle()%></a></td> 
     <td><%=b.getCreatedDate()%></td> 
     <td><%=b.getViewCount()%></td>
  </tr>
<% 
    }
%>
</table>

<form action='list' method='get'>
  <input type='text' name='keyword' value='<%=keyword != null ? keyword : ""%>'>
  <button>검색</button>
</form>

</body>
</html>
