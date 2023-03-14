package bitcamp.util;

public class RestResult {
  String status;
  String errorCode;
  Object data;

  public String getStatus() {
    return status;
  }
  public RestResult setStatus(String status) {
    this.status = status;
    return this;
  }
  public String getErrorCode() {
    return errorCode;
  }
  public RestResult setErrorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }
  public Object getData() {
    return data;
  }
  public RestResult setData(Object data) {
    this.data = data;
    return this;
  }
}
