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
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>게시판(JSP)</h1>
<% 
    int boardNo = Integer.parseInt(request.getParameter("no"));
    Board b = this.boardDao.findByNo(boardNo);

    if (b == null) {
%>
  <p>해당 번호의 게시글 없습니다.</p>
<% 
    } else {
      this.boardDao.increaseViewCount(boardNo);
%>
  <form id='board-form' action='update.jsp' method='post'>
  <table border='1'>
  <tr>
    <th>번호</th>
    <td><input type='text' name='no' value='<%=b.getNo()%>' readonly></td>
  </tr>
  <tr>
    <th>제목</th>
    <td><input type='text' name='title' value='<%=b.getTitle()%>'></td>
  </tr>
  <tr>
    <th>내용</th>
    <td><textarea name='content' rows='10' cols='60'><%=b.getContent()%></textarea></td>
  </tr>
  <tr>
    <th>암호</th>
    <td><input type='password' name='password'></td>
  </tr>
  <tr>
    <th>등록일</th>
    <td><%=b.getCreatedDate()%></td>
  </tr>
  <tr>
    <th>조회수</th>
    <td><%=b.getViewCount()%></td>
  </tr>
  </table>
<% 
    }
%>
<div>
  <button id='btn-list' type='button'>목록</button>
  <button>변경</button>
  <button id='btn-delete' type='button'>삭제</button>
</div>
</form>

<script>
document.querySelector('#btn-list').onclick = function() {
  location.href = 'list.jsp';
}

document.querySelector('#btn-delete').onclick = function() {
  var form = document.querySelector('#board-form');
  form.action = 'delete.jsp';
  form.submit();
}
</script>

</body>
</html>

