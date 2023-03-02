package bitcamp.util;

import java.lang.reflect.Method;

public class RequestHandlerMapping {
  public Object controller;
  public Method method;

  public RequestHandlerMapping() {
  }

  public RequestHandlerMapping(Object controller, Method method) {
    this.controller = controller;
    this.method = method;
  }
}
