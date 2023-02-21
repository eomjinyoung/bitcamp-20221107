<%@page import="bitcamp.myapp.dao.MemberDao"%>
<%@page import="bitcamp.util.TransactionManager"%>
<%@page import="bitcamp.myapp.vo.Teacher"%>
<%@page import="bitcamp.myapp.dao.TeacherDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
private TransactionManager txManager;
private MemberDao memberDao;
private TeacherDao teacherDao;

@Override
public void init() {
  ServletContext ctx = getServletContext();
  txManager = (TransactionManager) ctx.getAttribute("txManager");
  memberDao = (MemberDao) ctx.getAttribute("memberDao");
  teacherDao = (TeacherDao) ctx.getAttribute("teacherDao");
}
%>
<% 
    Teacher teacher = new Teacher();
    teacher.setNo(Integer.parseInt(request.getParameter("no")));
    teacher.setName(request.getParameter("name"));
    teacher.setEmail(request.getParameter("email"));
    teacher.setPassword(request.getParameter("password"));
    teacher.setTel(request.getParameter("tel"));
    teacher.setDegree(Integer.parseInt(request.getParameter("degree")));
    teacher.setSchool(request.getParameter("school"));
    teacher.setMajor(request.getParameter("major"));
    teacher.setWage(Integer.parseInt(request.getParameter("wage")));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<meta http-equiv='Refresh' content='1;url=list.jsp'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>강사</h1>
<% 
    txManager.startTransaction();
    try {
      if (memberDao.update(teacher) == 1 &&
          teacherDao.update(teacher) == 1) {
        txManager.commit();
%>
    <p>변경했습니다.</p>
<% 
      } else {
%>
    <p>해당 번호의 강사가 없습니다.</p>
<% 
      }
    } catch (Exception e) {
      txManager.rollback();
%>
  <p>변경 실패입니다.</p>
<% 
      e.printStackTrace();
    }
%>
</body>
</html>



