package software_capstone.backend.app.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software_capstone.backend.app.auth.document.OAuthProvider;
import software_capstone.backend.app.auth.document.Role;
import software_capstone.backend.app.auth.document.RefreshToken;
import software_capstone.backend.app.auth.document.User;
import software_capstone.backend.app.auth.dto.LoginRequest;
import software_capstone.backend.app.auth.dto.TokenResponse;
import software_capstone.backend.app.auth.jwt.TokenProvider;
import software_capstone.backend.app.auth.repository.RefreshTokenRepository;
import software_capstone.backend.app.auth.repository.UserRepository;
import software_capstone.backend.global.exception.ErrorMessage;
import software_capstone.backend.global.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoClient kakaoClient;
    private final NaverClient naverClient;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Transactional
    public TokenResponse kakaoLogin(LoginRequest request) {
        log.info("[Auth] 카카오 로그인 시도 - deviceInfo: {}", request.deviceInfo());
        String accessToken = kakaoClient.getAccessToken(request.code());
        KakaoClient.OAuthUserInfo userInfo = kakaoClient.getUserInfo(accessToken);
        return processLogin(OAuthProvider.KAKAO, userInfo.oauthId(), userInfo.name(), request.deviceInfo());
    }

    @Transactional
    public TokenResponse naverLogin(LoginRequest request) {
        log.info("[Auth] 네이버 로그인 시도 - deviceInfo: {}", request.deviceInfo());
        String accessToken = naverClient.getAccessToken(request.code());
        NaverClient.OAuthUserInfo userInfo = naverClient.getUserInfo(accessToken);
        return processLogin(OAuthProvider.NAVER, userInfo.oauthId(), userInfo.name(), request.deviceInfo());
    }

    @Transactional
    public TokenResponse refresh(String refreshToken) {
        log.info("[Auth] 토큰 재발급 요청");
        Claims claims = tokenProvider.parseClaimAllowExpired(refreshToken);

        if (claims.getExpiration().before(new Date())) {
            throw new UnauthorizedException(ErrorMessage.EXPIRED_TOKEN);
        }

        if (!tokenProvider.isRefreshToken(refreshToken)) {
            throw new UnauthorizedException(ErrorMessage.INVALID_TOKEN);
        }

        RefreshToken stored = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.INVALID_TOKEN));

        User user = userRepository.findById(stored.getUserId())
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.INVALID_TOKEN));

        refreshTokenRepository.deleteByToken(refreshToken);

        String newAccessToken = tokenProvider.createAccessToken(user.getId(), user.getRole().name());
        String newRefreshToken = tokenProvider.createRefreshToken(user.getId());
        saveRefreshToken(user.getId(), newRefreshToken, stored.getDeviceInfo());

        log.info("[Auth] 토큰 재발급 완료 - userId: {}", user.getId());
        return new TokenResponse(newAccessToken, newRefreshToken, false);
    }

    @Transactional
    public void logout(String refreshToken) {
        log.info("[Auth] 로그아웃 처리");
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    private TokenResponse processLogin(OAuthProvider provider, String oauthId, String name, String deviceInfo) {
        Optional<User> existing = userRepository.findByOauthProviderAndOauthId(provider, oauthId);
        boolean isNewUser = existing.isEmpty();

        User user = existing.orElseGet(() -> userRepository.save(User.builder()
                .oauthProvider(provider)
                .oauthId(oauthId)
                .name(name)
                .role(Role.USER)
                .isProActive(false)
                .build()));

        if (isNewUser) {
            log.info("[Auth] 신규 유저 생성 - provider: {}, oauthId: {}", provider, oauthId);
        } else {
            log.info("[Auth] 기존 유저 로그인 - userId: {}", user.getId());
        }

        String accessToken = tokenProvider.createAccessToken(user.getId(), user.getRole().name());
        String refreshToken = tokenProvider.createRefreshToken(user.getId());
        saveRefreshToken(user.getId(), refreshToken, deviceInfo);

        log.info("[Auth] 토큰 발급 완료 - userId: {}", user.getId());
        return new TokenResponse(accessToken, refreshToken, isNewUser);
    }

    private void saveRefreshToken(String userId, String token, String deviceInfo) {
        refreshTokenRepository.findByUserIdAndDeviceInfo(userId, deviceInfo)
                .ifPresent(existing -> refreshTokenRepository.deleteByToken(existing.getToken()));

        refreshTokenRepository.save(RefreshToken.builder()
                .userId(userId)
                .token(token)
                .deviceInfo(deviceInfo)
                .expiresAt(LocalDateTime.now().plus(refreshTokenExpiration, ChronoUnit.MILLIS))
                .build());
    }
}
