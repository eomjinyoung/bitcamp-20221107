package bitcamp.myapp.service;

import org.springframework.web.multipart.MultipartFile;

public interface ObjectStorageService {
  String uploadFile(String bucketName, String directoryPath, MultipartFile file);
}
