package software_capstone.backend.global.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @Qualifier("kakaoAuthWebClient")
    public WebClient kakaoAuthWebClient(WebClient.Builder builder) {
        return builder.clone()
                .baseUrl("https://kauth.kakao.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .build();
    }

    @Bean
    @Qualifier("kakaoApiWebClient")
    public WebClient kakaoApiWebClient(WebClient.Builder builder) {
        return builder.clone()
                .baseUrl("https://kapi.kakao.com")
                .build();
    }

    @Bean
    @Qualifier("naverAuthWebClient")
    public WebClient naverAuthWebClient(WebClient.Builder builder) {
        return builder.clone()
                .baseUrl("https://nid.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                .build();
    }

    @Bean
    @Qualifier("naverApiWebClient")
    public WebClient naverApiWebClient(WebClient.Builder builder) {
        return builder.clone()
                .baseUrl("https://openapi.naver.com")
                .build();
    }
}
