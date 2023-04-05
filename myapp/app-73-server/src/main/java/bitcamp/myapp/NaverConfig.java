package bitcamp.myapp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Configuration
@PropertySource("classpath:/naver.properties")
@ConfigurationProperties(prefix="ncp")
@Getter
@Setter
@ToString
public class NaverConfig {
  //  Logger log = LogManager.getLogger(getClass());
  //
  //  public NaverConfig() {
  //    log.debug("NaverConfig 객체 생성됨!");
  //  }

  // 프로퍼티 파일에서 선언된 값 중에서
  // ncp.* 이름으로 된 프로퍼티 값을 받을 필드를 선언한다.
  private String endPoint; // ncp.endPoint 프로퍼티를 받는 필드
  private String regionName; // ncp.regionName 프로퍼티를 받는 필드
  private String accessKey; // ncp.accessKey 프로퍼티를 받는 필드
  private String secretKey; // ncp.secretKey 프로퍼티를 받는 필드
}
