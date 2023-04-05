package bitcamp.myapp.service.impl;

import java.io.InputStream;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import bitcamp.myapp.NaverConfig;
import bitcamp.myapp.service.ObjectStorageService;

@Service
public class NcpObjectStorageService implements ObjectStorageService {

  Logger log = LogManager.getLogger(getClass());

  final AmazonS3 s3;

  public NcpObjectStorageService(NaverConfig naverConfig) {
    s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
            naverConfig.getEndPoint(), naverConfig.getRegionName()))
        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
            naverConfig.getAccessKey(), naverConfig.getSecretKey())))
        .build();
  }

  @Override
  public String uploadFile(String bucketName, String directoryPath, MultipartFile file) {
    if (file.isEmpty()) {
      return null;
    }

    try (InputStream fileIn = file.getInputStream()) {
      String filename = UUID.randomUUID().toString();

      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentType(file.getContentType());

      PutObjectRequest objectRequest = new PutObjectRequest(
          bucketName,
          directoryPath + filename,
          fileIn,
          objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);

      s3.putObject(objectRequest);

      //return s3.getUrl(bucketName, directoryPath + filename).toString();
      return filename;

    } catch (Exception e) {
      throw new RuntimeException("파일 업로드 오류", e);
    }
  }

}
