package software_capstone.backend.app.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software_capstone.backend.app.auth.dto.LoginRequest;
import software_capstone.backend.app.auth.dto.RefreshRequest;
import software_capstone.backend.app.auth.dto.TokenResponse;
import software_capstone.backend.app.auth.service.AuthService;

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

    @Operation(summary = "카카오 로그인 콜백", description = "카카오 OAuth 리다이렉트 콜백 엔드포인트입니다.")
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
            @RequestParam String state,
            @RequestParam(defaultValue = "web") String deviceInfo) {
        return ResponseEntity.ok(authService.naverLogin(new LoginRequest(code, deviceInfo)));
    }
}
