<%@ page import="bitcamp.myapp.vo.Board"%>
<%@ page import="bitcamp.myapp.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
  private BoardDao boardDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    boardDao = (BoardDao) ctx.getAttribute("boardDao");
  }
%>
<% 
    int boardNo = Integer.parseInt(request.getParameter("no"));
    String password = request.getParameter("password");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<meta http-equiv='Refresh' content='1;url=list.jsp'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>게시판(JSP)</h1>
<% 
    Board old = boardDao.findByNo(boardNo);
    if (old == null) {
%>
  <p>해당 번호의 게시글이 없습니다.</p>
<% 
    } else if (!old.getPassword().equals(password)) {
%>
  <p>암호가 맞지 않습니다!</p>
<% 
    } else {
      this.boardDao.delete(boardNo);
%>
  <p>삭제했습니다.</p>
<%   
    }
%>
</body>
</html>


