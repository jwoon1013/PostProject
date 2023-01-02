package com.hannunpalzi.postproject.service;

import com.hannunpalzi.postproject.jwtUtil.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenChecker { // token에서 유저 검증 위한 tokenChecker 클래스 (Security 구현 후 수정 예정)
    private final JwtUtil jwtUtil;

    public String checkToken(String token){
        // 1. Claims 생성
        Claims claims;
        // 2. 토큰이 있는지, 유효한지 체크
        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("토큰값이 유효하지 않음");
            }
            // 3. username 반환 (*claims의 subejct가 username임. claims를 반환하지 않기 위해 username 반환)
            return claims.getSubject(); // claims의 subejdc
        } else throw new IllegalArgumentException("토큰값이 null 입니다.");
    }
}
