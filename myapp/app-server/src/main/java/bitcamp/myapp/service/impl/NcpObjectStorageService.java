package bitcamp.myapp.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import bitcamp.myapp.service.ObjectStorageService;

@Service
public class NcpObjectStorageService implements ObjectStorageService {

  final String endPoint = "https://kr.object.ncloudstorage.com";
  final String regionName = "kr-standard";
  final String accessKey = "";
  final String secretKey = "";

  @Override
  public String uploadFile(MultipartFile file) {
    // TODO Auto-generated method stub

    return null;
  }

}
