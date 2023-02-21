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
    Board board = new Board();
    board.setNo(Integer.parseInt(request.getParameter("no")));
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));
    board.setPassword(request.getParameter("password"));
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
    Board old = boardDao.findByNo(board.getNo());
    if (old == null) {
%>
  <p>해당 번호의 게시글이 없습니다.</p>
<% 
    } else if (!old.getPassword().equals(board.getPassword())) {
%>
  <p>암호가 맞지 않습니다!</p>
<% 
    } else {
      this.boardDao.update(board);
%>
  <p>변경했습니다.</p>
<% 
    }
%>
</body>
</html>


