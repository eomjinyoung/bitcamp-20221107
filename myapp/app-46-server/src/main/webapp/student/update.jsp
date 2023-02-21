<%@page import="bitcamp.myapp.dao.MemberDao"%>
<%@page import="bitcamp.util.TransactionManager"%>
<%@page import="bitcamp.myapp.vo.Student"%>
<%@page import="bitcamp.myapp.dao.StudentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
private TransactionManager txManager;
private MemberDao memberDao;
private StudentDao studentDao;

@Override
public void init() {
  ServletContext ctx = getServletContext();
  txManager = (TransactionManager) ctx.getAttribute("txManager");
  memberDao = (MemberDao) ctx.getAttribute("memberDao");
  studentDao = (StudentDao) ctx.getAttribute("studentDao");
}
%>
<% 
    Student student = new Student();
    student.setNo(Integer.parseInt(request.getParameter("no")));
    student.setName(request.getParameter("name"));
    student.setEmail(request.getParameter("email"));
    student.setPassword(request.getParameter("password"));
    student.setTel(request.getParameter("tel"));
    student.setPostNo(request.getParameter("postNo"));
    student.setBasicAddress(request.getParameter("basicAddress"));
    student.setDetailAddress(request.getParameter("detailAddress"));
    student.setWorking(request.getParameter("working") != null);
    student.setGender(request.getParameter("gender").charAt(0));
    student.setLevel(Byte.parseByte(request.getParameter("level")));
%>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<meta http-equiv='Refresh' content='1;url=list.jsp'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>학생(JSP)</h1>
<% 
    txManager.startTransaction();
    try {
      if (memberDao.update(student) == 1 &&
          studentDao.update(student) == 1) {
        txManager.commit();
%>
    <p>변경했습니다.</p>
<% 
      } else {
%>
    <p>해당 번호의 학생이 없습니다.</p>
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


