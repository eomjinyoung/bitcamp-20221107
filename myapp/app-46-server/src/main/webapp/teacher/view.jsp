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
%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>비트캠프 - NCP 1기</title>
</head>
<body>
<h1>강사(JSP)</h1>
<% 
    int teacherNo = Integer.parseInt(request.getParameter("no"));
    Teacher teacher = this.teacherDao.findByNo(teacherNo);

    if (teacher == null) {
%>
  <p>해당 번호의 강사가 없습니다.</p>
<% 
    } else {
%>
  <form id='teacher-form' action='update.jsp' method='post'>

  <table border='1'>

  <tr>
    <th>번호</th>
    <td><input type='text' name='no' value='<%=teacher.getNo()%>' readonly></td>
  </tr>

  <tr>
    <th>이름</th>
    <td><input type='text' name='name' value='<%=teacher.getName()%>'></td>
  </tr>

  <tr>
    <th>이메일</th>
    <td><input type='email' name='email' value='<%=teacher.getEmail()%>'></td>
  </tr>

  <tr>
    <th>암호</th>
    <td><input type='password' name='password'></td>
  </tr>

  <tr>
    <th>전화</th>
    <td><input type='tel' name='tel' value='<%=teacher.getTel()%>'></td>
  </tr>

  <tr>
    <th>학위</th>
    <td><select name='degree'>
        <option value='1' <%=teacher.getDegree() == 1 ? "selected" : ""%>>고졸</option>
        <option value='2' <%=teacher.getDegree() == 2 ? "selected" : ""%>>전문학사</option>
        <option value='3' <%=teacher.getDegree() == 3 ? "selected" : ""%>>학사</option>
        <option value='4' <%=teacher.getDegree() == 4 ? "selected" : ""%>>석사</option>
        <option value='5' <%=teacher.getDegree() == 5 ? "selected" : ""%>>박사</option>
        <option value='0' <%=teacher.getDegree() == 0 ? "selected" : ""%>>기타</option>
        </select></td>
  </tr>

  <tr>
    <th>학교</th>
    <td><input type='text' name='school' value='<%=teacher.getSchool()%>'></td>
  </tr>

  <tr>
    <th>전공</th>
    <td><input type='text' name='major' value='<%=teacher.getMajor()%>'></td>
  </tr>

  <tr>
    <th>강의료(시급)</th>
    <td><input type='number' name='wage' value='<%=teacher.getWage()%>'></td>
  </tr>

  <tr>
    <th>등록일</th>
    <td><%=teacher.getCreatedDate()%></td>
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
  var form = document.querySelector('#teacher-form');
  form.action = 'delete.jsp';
  form.submit();
}
</script>

</body>
</html>


