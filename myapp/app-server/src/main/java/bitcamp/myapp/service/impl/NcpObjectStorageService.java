package bitcamp.myapp.service.impl;

import java.io.IOException;
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
  final String bucketName = "bitcamp-bucket28";

  public NcpObjectStorageService(NaverConfig naverConfig) {
    s3 = AmazonS3ClientBuilder.standard()
        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
            naverConfig.getEndPoint(), naverConfig.getRegionName()))
        .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(
            naverConfig.getAccessKey(), naverConfig.getSecretKey())))
        .build();
  }

  @Override
  public String uploadFile(MultipartFile file) {
    if (file.isEmpty()) {
      return null;
    }

    String filename = UUID.randomUUID().toString();

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(file.getContentType());

    try (InputStream inputStream = file.getInputStream()) {
      s3.putObject(new PutObjectRequest(bucketName, filename, inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return s3.getUrl(bucketName, filename).toString();
  }

}
