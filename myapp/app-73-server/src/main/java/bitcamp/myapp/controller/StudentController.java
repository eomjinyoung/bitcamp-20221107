package bitcamp.myapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import bitcamp.myapp.service.ObjectStorageService;
import bitcamp.myapp.service.StudentService;
import bitcamp.myapp.vo.Student;
import bitcamp.util.RestResult;
import bitcamp.util.RestStatus;

@RestController
@RequestMapping("/students")
public class StudentController {

  Logger log = LogManager.getLogger(getClass());

  {
    log.trace("StudentController 생성됨!");
  }

  @Autowired private StudentService studentService;
  @Autowired private ObjectStorageService objectStorageService;
  private String bucketName = "bitcamp-bucket28-member-photo";

  @PostMapping
  public Object insert(Student student, MultipartFile file) {
    String filename = objectStorageService.uploadFile(bucketName, "", file);
    if (filename != null) {
      student.setPhoto(filename);
    }

    studentService.add(student);
    return new RestResult()
        .setStatus(RestStatus.SUCCESS);
  }

  @GetMapping
  public Object list(String keyword) {
    return new RestResult()
        .setStatus(RestStatus.SUCCESS)
        .setData(studentService.list(keyword));
  }

  @GetMapping("{no}")
  public Object view(@PathVariable int no) {
    return new RestResult()
        .setStatus(RestStatus.SUCCESS)
        .setData(studentService.get(no));
  }

  @PutMapping("{no}")
  public Object update(
      @PathVariable int no,
      Student student,
      MultipartFile file) {

    String filename = objectStorageService.uploadFile(bucketName, "", file);
    if (filename != null) {
      student.setPhoto(filename);
    }

    log.debug(student);

    // 보안을 위해 URL 번호를 게시글 번호로 설정한다.
    student.setNo(no);

    studentService.update(student);
    return new RestResult()
        .setStatus(RestStatus.SUCCESS);
  }

  @DeleteMapping("{no}")
  public Object delete(@PathVariable int no) {
    studentService.delete(no);
    return new RestResult()
        .setStatus(RestStatus.SUCCESS);
  }
}
