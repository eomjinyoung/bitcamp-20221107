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
    int studentNo = Integer.parseInt(request.getParameter("no"));
    txManager.startTransaction();
    try {
      if (studentDao.delete(studentNo) == 1 &&
          memberDao.delete(studentNo) == 1) {
        txManager.commit();
%>
    <p>삭제했습니다.</p>
<% 
      } else {
%>
    <p>해당 번호의 학생이 없습니다.</p>
<% 
      }
    } catch (Exception e) {
      txManager.rollback();
      e.printStackTrace();
%>
  <p>삭제 실패입니다.</p>
<% 
    }
%>
</body>
</html>



