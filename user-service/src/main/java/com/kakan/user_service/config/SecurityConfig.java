package com.kakan.user_service.config;

import com.kakan.user_service.dto.response.AccountResponse;
import com.kakan.user_service.service.impl.AccountServiceImpl;
import com.kakan.user_service.service.impl.AuthenticationServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Autowired
    private JwtFilter jwtFilter;

    @Value("${frontend.url}")
    String frontendUrl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/login", "/api/register", "/api/loginByGoogle",
                                "/oauth2/authorization/**", "/login/oauth2/code/**",
                                "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/api/vnpay-return").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oauth2UserService()))
                        .successHandler(authenticationSuccessHandler())
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        return authenticationProvider;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

            // Gọi service để xử lý logic đăng nhập và tạo token
            AccountResponse accountResponse = authenticationService.processOAuthPostLogin(oAuth2User);

            if (accountResponse == null) {
                // Nếu tài khoản bị vô hiệu hóa, chuyển hướng với thông báo lỗi
                String redirectUrl = "https://nguyenhoangan.site/login/success?error=disabled";
//                String redirectUrl = frontendUrl + "/login/success?error=disabled";

                response.sendRedirect(redirectUrl);
                return;
            }

            //Mã hóa các thông tin để gửi qua URL
            String encodedToken = URLEncoder.encode(accountResponse.getToken(), "UTF-8");
            String encodedUsername = URLEncoder.encode(accountResponse.getUserName(), "UTF-8");
            String role = URLEncoder.encode(accountResponse.getRole(), "UTF-8");
            String encodedId = URLEncoder.encode(String.valueOf(accountResponse.getId()), "UTF-8");

            String redirectUrl = "https://nguyenhoangan.site/login/success?token=" +
                    encodedToken + "&username=" + encodedUsername + "&role=" + role + "id=" + encodedId;
//            String redirectUrl = frontendUrl + "/login/success?token=" +
//                    encodedToken + "&username=" + encodedUsername + "&role=" + role + "id=" + encodedId;

//            Map<String, String> tokenMap = new HashMap<>();
//            tokenMap.put("access_token", accountResponse.getToken());
//
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            objectMapper.writeValue(response.getWriter(), tokenMap);
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new DefaultOAuth2UserService();
    }
}
