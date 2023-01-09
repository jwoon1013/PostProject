package com.hannunpalzi.postproject.jwtUtil;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hannunpalzi.postproject.entity.User;
import com.hannunpalzi.postproject.repository.UserRepository;
import com.hannunpalzi.postproject.security.SecurityExceptionDto;
import com.hannunpalzi.postproject.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshJwt refreshJwt;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);
        String refreshToken = refreshJwt.resolveRefreshToken(request);

        if(token != null) {
            if(!jwtUtil.validateToken(token)){
                try{
                    String username = refreshJwt.getUserInfoFromToken(refreshToken).getSubject();
                    User user = userRepository.findByUsername(username).orElseThrow();

                    if(refreshJwt.validateToken(user.getRefreshToken().substring(7))){
                        if(user.getRefreshToken().substring(7).equals(refreshToken)) {
                            response.addHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(user.getUsername(), user.getRole()));
                            jwtExceptionHandler(response, "Token is reissuance. Please send again.", HttpStatus.UNAUTHORIZED.value());
                            return;
                        }
                    }
                }
                catch (UsernameNotFoundException | IllegalArgumentException e){
                    jwtExceptionHandler(response, "Token Error, Please login again", HttpStatus.UNAUTHORIZED.value());
                    return;
                }
                jwtExceptionHandler(response, "Token Error, Please login again", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(token);
            setAuthentication(info.getSubject());
        }
        filterChain.doFilter(request,response);
    }


    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = this.createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
