package bitcamp.myapp.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig(maxFileSize = 1024 * 1024 * 50)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // 요청 URL => http://localhost:8080/web/app/board/list
    // - Context Root: /web
    // - ServletPath: /app
    // - PathInfo: /board/list
    String pathInfo = request.getPathInfo();

    // 페이지 컨트롤러 실행
    if (pathInfo.equals("/")) {
      response.sendRedirect(request.getContextPath() + "/");
      return;
    }
    request.getRequestDispatcher(pathInfo).include(request, response);

    String view = (String) request.getAttribute("view");

    // 뷰 컴포넌트 실행
    if (view != null) {
      if (view.startsWith("redirect:")) {
        response.sendRedirect(view.substring(9));
      } else {
        request.getRequestDispatcher(view).forward(request, response);
      }
    }

  }
}






