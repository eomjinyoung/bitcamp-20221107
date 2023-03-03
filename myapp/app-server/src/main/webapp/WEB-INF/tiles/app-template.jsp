<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<c:if test="${not empty refresh}">
  <meta http-equiv='Refresh' content='1;url=${refresh}'>
</c:if>
<title>비트캠프 - NCP 1기</title>
<style>
  header {
    height: 60px;
    background-color: gray;
    color: black;
  }
  
  footer {
    height: 60px;
    background-color: navy;
    color: white;
  }
</style>
</head>
<body>

<tiles:insertAttribute name="header"/>

<div class='container'>
<tiles:insertAttribute name="body"/>
</div>

<tiles:insertAttribute name="footer"/>

</body>
</html>















