<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>게시판(Tiles)</h1>

<div><a href='form'>새 글</a></div>

<table border='1'>
<tr>
  <th>번호</th> <th>제목</th> <th>작성자</th> <th>작성일</th> <th>조회수</th>
</tr>

<c:forEach items="${boards}" var="b">
  <tr>
     <td>${b.no}</td> 
     <td><a href='view?no=${b.no}'>${b.title}</a></td> 
     <td>${b.writer.name}</td>
     <td>${b.createdDate}</td> 
     <td>${b.viewCount} </td>
  </tr>
</c:forEach>

</table>

<form action='list' method='get'>
  <input type='text' name='keyword' value='${param.keyword}'>
  <button>검색</button>
</form>















