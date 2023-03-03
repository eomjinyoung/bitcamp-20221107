<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>게시판(Tiles)</h1>

<c:if test="${empty board}">
  <p>해당 번호의 게시글 없습니다.</p>
  <div>
    <button id='btn-list' type='button'>목록</button>
  </div>
</c:if>

<c:if test="${not empty board}">
  <form id='board-form' action='update' method='post' enctype="multipart/form-data">
  <table border='1'>
  <tr>
    <th>번호</th>
    <td><input type='text' name='no' value='${board.no}' readonly></td>
  </tr>
  <tr>
    <th>제목</th>
    <td><input type='text' name='title' value='${board.title}'></td>
  </tr>
  <tr>
    <th>내용</th>
    <td><textarea name='content' rows='10' cols='60'>${board.content}</textarea></td>
  </tr>
  <tr>
    <th>작성자</th>
    <td>${board.writer.name}</td>
  </tr>
  <tr>
    <th>등록일</th>
    <td>${board.createdDate}</td>
  </tr>
  <tr>
    <th>조회수</th>
    <td>${board.viewCount}</td>
  </tr>
  <tr>
    <th>첨부파일</th>
    <td>
      <input type="file" name='files' multiple>
      <ul>
      <c:forEach items="${board.attachedFiles}" var="boardFile">
        <c:if test="${boardFile.no != 0}">
          <li>
            <a href="../download/boardfile?fileNo=${boardFile.no}">${boardFile.originalFilename}</a>
            [<a href="filedelete?boardNo=${board.no}&fileNo=${boardFile.no}">삭제</a>]
          </li>
        </c:if>
      </c:forEach>
      </ul>
    </td>
  </tr>
  </table>
	
	<div>
	  <button id='btn-list' type='button'>목록</button>
	<c:if test="${loginUser.no == board.writer.no}">
	  <button>변경</button>
	  <button id='btn-delete' type='button'>삭제</button>
	</c:if>
	</div>
	</form>
</c:if>


<script>
document.querySelector('#btn-list').onclick = function() {
  location.href = 'list';
}

<c:if test="${not empty board}">
document.querySelector('#btn-delete').onclick = function() {
  var form = document.querySelector('#board-form');
  form.action = 'delete';
  form.submit();
}
</c:if>
</script>

