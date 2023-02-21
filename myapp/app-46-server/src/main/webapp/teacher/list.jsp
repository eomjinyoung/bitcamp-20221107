<%@page import="bitcamp.myapp.vo.Teacher"%>
<%@page import="java.util.List"%>
<%@page import="bitcamp.myapp.dao.TeacherDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
  private TeacherDao teacherDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    teacherDao = (TeacherDao) ctx.getAttribute("teacherDao");
  }
  
  private static String getDegreeText(int degree) {
    switch (degree) {
      case 1: return "고졸";
      case 2: return "전문학사";
      case 3: return "학사";
      case 4: return "석사";
      case 5: return "박사";
      default: return "기타";
    }
  }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>강사(JSP)</h1>

<div><a href='form.jsp'>새 강사</a></div>

<table border='1'>
<tr>
  <th>번호</th> <th>이름</th> <th>전화</th> <th>학위</th> <th>전공</th> <th>시강료</th>
</tr>
<% 
    List<Teacher> teachers = this.teacherDao.findAll();
    for (Teacher teacher : teachers) {
%>
  <tr>
      <td><%=teacher.getNo()%></td> 
      <td><a href='view.jsp?no=<%=teacher.getNo()%>'><%=teacher.getName()%></a></td> 
      <td><%=teacher.getTel()%></td> 
      <td><%=getDegreeText(teacher.getDegree())%></td> 
      <td><%=teacher.getMajor()%></td> 
      <td><%=teacher.getWage()%></td>
  </tr>
<% 
    }
%>
</table>

</body>
</html>


