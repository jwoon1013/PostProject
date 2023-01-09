package com.hannunpalzi.postproject.config;

import com.hannunpalzi.postproject.jwtUtil.JwtAuthFilter;
import com.hannunpalzi.postproject.jwtUtil.JwtUtil;
import com.hannunpalzi.postproject.jwtUtil.RefreshJwt;
import com.hannunpalzi.postproject.repository.UserRepository;
import com.hannunpalzi.postproject.security.CustomAccessDeniedHandler;
import com.hannunpalzi.postproject.security.CustomAuthenticationEntryPoint;
import com.hannunpalzi.postproject.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtUtil jwtUtil;
    private final RefreshJwt refreshJwt;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers(HttpMethod.GET, "/categories/**","/posts/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests().antMatchers("/users/signup").permitAll()
                .antMatchers("/admin/signup").permitAll()
                .antMatchers("/users/login").permitAll()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        /* swagger v3 */
                        "/v3/api-docs/**",
                        "/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/categories/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/categories/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/categories/**").hasAnyRole("ADMIN")
                .antMatchers("/admin/posts/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil, userDetailsService,refreshJwt,userRepository), UsernamePasswordAuthenticationFilter.class);

        //401 인증과정 실패시 에러처리
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        //403
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        return http.build();
    }
}
