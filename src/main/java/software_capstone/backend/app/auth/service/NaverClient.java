package software_capstone.backend.app.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverClient {

    private static final String TOKEN_URL = "https://nid.naver.com/oauth2.0/token";
    private static final String USER_INFO_URL = "https://openapi.naver.com/v1/nid/me";

    public record OAuthUserInfo(String oauthId, String name) {}

    private final WebClient webClient;

    @Value("${oauth.naver.client-id}")
    private String clientId;

    @Value("${oauth.naver.client-secret}")
    private String clientSecret;

    @Value("${oauth.naver.redirect-uri}")
    private String redirectUri;

    @SuppressWarnings("unchecked")
    public String getAccessToken(String code) {
        log.info("[Naver] 액세스 토큰 요청 - redirect_uri: {}", redirectUri);

        String uri = UriComponentsBuilder.fromUriString(TOKEN_URL)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", clientSecret)
                .queryParam("code", code)
                .queryParam("redirect_uri", redirectUri)
                .toUriString();

        Map<String, Object> response = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        log.info("[Naver] 액세스 토큰 발급 성공");
        return (String) response.get("access_token");
    }

    @SuppressWarnings("unchecked")
    public OAuthUserInfo getUserInfo(String accessToken) {
        log.info("[Naver] 사용자 정보 요청");

        Map<String, Object> response = webClient.get()
                .uri(USER_INFO_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        Map<String, String> naverResponse = (Map<String, String>) response.get("response");
        log.info("[Naver] 사용자 정보 조회 성공 - oauthId: {}", naverResponse.get("id"));
        return new OAuthUserInfo(naverResponse.get("id"), naverResponse.get("name"));
    }
}
