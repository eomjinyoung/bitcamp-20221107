package bitcamp.myapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@ConfigurationProperties(prefix="ncp")
@PropertySource("classpath:/naver.properties")
@Getter
@Setter
@ToString
public class NaverConfig {
  Logger log = LogManager.getLogger(getClass());

  private String endPoint;
  private String regionName;
  private String accessKey;
  private String secretKey;
}
