<%@page import="bitcamp.myapp.vo.Student"%>
<%@page import="bitcamp.myapp.dao.StudentDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
  private StudentDao studentDao;

  @Override
  public void init() {
    ServletContext ctx = getServletContext();
    studentDao = (StudentDao) ctx.getAttribute("studentDao");
  }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>학생(JSP)</h1>
<% 
    int studentNo = Integer.parseInt(request.getParameter("no"));
    Student student = this.studentDao.findByNo(studentNo);

    if (student == null) {
%>
  <p>해당 번호의 학생이 없습니다.</p>
<% 
    } else {
%>
  <form id='student-form' action='update.jsp' method='post'>

  <table border='1'>

  <tr>
    <th>번호</th>
    <td><input type='text' name='no' value='<%=student.getNo()%>' readonly></td>
  </tr>

  <tr>
    <th>이름</th>
    <td><input type='text' name='name' value='<%=student.getName()%>'></td>
  </tr>

  <tr>
    <th>이메일</th>
    <td><input type='email' name='email' value='<%=student.getEmail()%>'></td>
  </tr>

  <tr>
    <th>암호</th>
    <td><input type='password' name='password'></td>
  </tr>

  <tr>
    <th>전화</th>
    <td><input type='tel' name='tel' value='<%=student.getTel()%>'></td>
  </tr>

  <tr>
    <th>우편번호</th>
    <td><input type='text' name='postNo' value='<%=student.getPostNo()%>'></td>
  </tr>

  <tr>
    <th>기본주소</th>
    <td><input type='text' name='basicAddress' value='<%=student.getBasicAddress()%>'></td>
  </tr>

  <tr>
    <th>상세주소</th>
    <td><input type='tel' name='detailAddress' value='<%=student.getDetailAddress()%>'></td>
  </tr>

  <tr>
    <th>재직여부</th>
    <td><input type='checkbox' name='working' <%=student.isWorking() ? "checked" : ""%>> 재직중</td>
  </tr>

  <tr>
    <th>성별</th>
    <td><input type='radio' name='gender' value='M' <%=student.getGender() == 'M' ? "checked" : ""%>>
    <input type='radio' name='gender' value='W' <%=student.getGender() == 'W' ? "checked" : ""%>> 여</td>

  </tr>

  <tr>
    <th>전공</th>
    <td><select name='level'>"
    <option value='0' <%=student.getLevel() == 0 ? "selected" : ""%>>비전공자</option>
    <option value='1' <%=student.getLevel() == 1 ? "selected" : ""%>>준전공자</option>
    <option value='2' <%=student.getLevel() == 2 ? "selected" : ""%>>전공자</option>
    </select></td>
  </tr>

  <tr>
    <th>등록일</th>
    <td><%=student.getCreatedDate()%></td>
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
  var form = document.querySelector('#student-form');
  form.action = 'delete.jsp';
  form.submit();
}
</script>

</body>
</html>


