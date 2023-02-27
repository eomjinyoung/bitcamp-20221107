package bitcamp.myapp.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import bitcamp.util.RequestHandlerMapping;
import bitcamp.util.RequestParam;

@MultipartConfig(maxFileSize = 1024 * 1024 * 50)
@WebServlet("/app/*")
public class DispatcherServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    try {
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

      ServletContext ctx = getServletContext();

      // 클라이언트가 요청한 URL을 가지고 페이지 컨트롤러의 요청 핸들러를 찾는다.
      RequestHandlerMapping requestHandlerMapping = (RequestHandlerMapping) ctx.getAttribute(pathInfo);
      if (requestHandlerMapping == null) {
        request.getRequestDispatcher("/NotFoundController.jsp").forward(request, response);
        return;
      }

      // 요청 핸들러 실행
      Object[] arguments = prepareRequestHandlerArguments(requestHandlerMapping.method, request, response);
      String view = (String) requestHandlerMapping.method.invoke(
          requestHandlerMapping.controller, arguments); // controller.execute(request, response)

      // 쿠키 처리
      @SuppressWarnings("unchecked")
      List<Cookie> cookies = (List<Cookie>) request.getAttribute("cookies");
      if (cookies != null) {
        for (Cookie cookie : cookies) {
          response.addCookie(cookie);
        }
      }

      // 뷰 컴포넌트 실행
      if (view != null) {
        if (view.startsWith("redirect:")) {
          response.sendRedirect(view.substring(9));
        } else {
          request.getRequestDispatcher(view).forward(request, response);
        }
      }
    } catch (Exception e) {
      request.getRequestDispatcher("/ServerError.jsp").forward(request, response);
      e.printStackTrace();
    }

  }

  private Object[] prepareRequestHandlerArguments(
      Method method,
      HttpServletRequest request,
      HttpServletResponse response) throws Exception {

    Parameter[] params = method.getParameters();
    Object[] arguments = new Object[params.length];

    System.out.println("파라미터 값 준비함!");
    for (int i = 0; i < params.length; i++) {
      Class<?> paramType = params[i].getType();

      // 파라미터의 타입에 따라 전달될 아규먼트(값)을 준비한다.
      if (paramType == HttpSession.class) {
        arguments[i] = request.getSession();
        System.out.println("    - 세션 객체 준비");
      } else if (paramType  == HttpServletRequest.class || paramType  == ServletRequest.class) {
        arguments[i] = request;
        System.out.println("    - 요청 객체 준비");
      } else if (paramType  == HttpServletResponse.class || paramType == ServletResponse.class) {
        arguments[i] = response;
        System.out.println("    - 응답 객체 준비");
      } else {
        RequestParam anno = params[i].getAnnotation(RequestParam.class);
        if (anno != null) {
          String paramName = anno.value();
          String paramValue = request.getParameter(paramName);

          if (paramType == String.class) {
            arguments[i] = paramValue;
          } else if (paramType == byte.class || paramType == Byte.class) {
            arguments[i] = Byte.parseByte(paramValue);
          } else if (paramType == char.class || paramType == Character.class) {
            arguments[i] = paramValue.charAt(0);
          } else if (paramType == int.class || paramType == Integer.class) {
            arguments[i] = Integer.parseInt(paramValue);
          } else if (paramType == float.class || paramType == Float.class) {
            arguments[i] = Float.parseFloat(paramValue);
          } else if (paramType == boolean.class || paramType == Boolean.class) {
            arguments[i] = paramValue != null;
          } else if (paramType == Part.class) {
            arguments[i] = request.getPart(paramName);
          } else if (paramType == Part[].class) {
            Collection<Part> parts = request.getParts();
            List<Part> paramParts = new ArrayList<>();
            for (Part part : parts) {
              if (!part.getName().equals(paramName)) {
                continue;
              }
              paramParts.add(part);
            }
            arguments[i] = paramParts.toArray(new Part[] {});
          }
          System.out.println("    - 요청 파라미터 값 준비");
        } else {
          arguments[i] = null;
        }
      }
    }
    return arguments;
  }
}






