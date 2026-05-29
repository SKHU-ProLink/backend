package software_capstone.backend.app.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import software_capstone.backend.app.auth.dto.LoginRequest;
import software_capstone.backend.app.auth.dto.RefreshRequest;
import software_capstone.backend.app.auth.dto.TokenResponse;
import software_capstone.backend.app.auth.jwt.TokenProvider;
import software_capstone.backend.app.auth.service.AuthService;

import java.net.URI;

@Tag(name = "Auth", description = "소셜 로그인 및 토큰 관리 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "카카오 로그인", description = "카카오 인가코드로 로그인하고 JWT 토큰을 발급합니다.")
    @PostMapping("/kakao")
    public ResponseEntity<TokenResponse> kakaoLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.kakaoLogin(request));
    }

    @Operation(summary = "네이버 로그인", description = "네이버 인가코드로 로그인하고 JWT 토큰을 발급합니다.")
    @PostMapping("/naver")
    public ResponseEntity<TokenResponse> naverLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.naverLogin(request));
    }

    @Operation(summary = "토큰 재발급", description = "리프레시 토큰으로 새 액세스/리프레시 토큰을 발급합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request.refreshToken()));
    }

    @Operation(summary = "로그아웃", description = "리프레시 토큰을 삭제하여 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshRequest request) {
        authService.logout(request.refreshToken());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "회원탈퇴", description = "리프레시 토큰과 유저를 삭제하여 회원 탈퇴합니다.")
    @DeleteMapping
    public ResponseEntity<Void> delete(@AuthenticationPrincipal TokenProvider.AuthUser authUser) {
        authService.deleteUser(authUser.userId());
        return ResponseEntity.ok().build();
    }

    /*@Operation(summary = "카카오 로그인 콜백", description = "카카오 OAuth 리다이렉트 콜백 엔드포인트입니다.")
    @GetMapping("/kakao/callback")
    public ResponseEntity<TokenResponse> kakaoCallback(
            @RequestParam String code,
            @RequestParam(defaultValue = "web") String deviceInfo) {
        return ResponseEntity.ok(authService.kakaoLogin(new LoginRequest(code, deviceInfo)));
    }

    @Operation(summary = "네이버 로그인 콜백", description = "네이버 OAuth 리다이렉트 콜백 엔드포인트입니다.")
    @GetMapping("/naver/callback")
    public ResponseEntity<TokenResponse> naverCallback(
            @RequestParam String code,
            @RequestParam(defaultValue = "web") String deviceInfo) {
        return ResponseEntity.ok(authService.naverLogin(new LoginRequest(code, deviceInfo)));
    }*/

    @Operation(summary = "카카오 로그인 콜백", description = "카카오 OAuth 리다이렉트 후 딥링크로 토큰을 전달합니다.")
    @GetMapping("/kakao/callback")
    public ResponseEntity<Void> kakaoCallback(
            @RequestParam String code,
            @RequestParam(defaultValue = "web") String deviceInfo) {
        TokenResponse token = authService.kakaoLogin(new LoginRequest(code, deviceInfo));
        URI redirectUri = UriComponentsBuilder
                .fromUriString("frontend://auth/kakao/callback")
                .queryParam("accessToken", token.accessToken())
                .queryParam("refreshToken", token.refreshToken())
                .queryParam("isNewUser", token.isNewUser())
                .build().toUri();
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }

    @Operation(summary = "네이버 로그인 콜백", description = "네이버 OAuth 리다이렉트 후 딥링크로 토큰을 전달합니다.")
    @GetMapping("/naver/callback")
    public ResponseEntity<Void> naverCallback(
            @RequestParam String code,
            @RequestParam(defaultValue = "web") String deviceInfo) {
        TokenResponse token = authService.naverLogin(new LoginRequest(code, deviceInfo));
        URI redirectUri = UriComponentsBuilder
                .fromUriString("frontend://auth/naver/callback")
                .queryParam("accessToken", token.accessToken())
                .queryParam("refreshToken", token.refreshToken())
                .queryParam("isNewUser", token.isNewUser())
                .build().toUri();
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();
    }
}
