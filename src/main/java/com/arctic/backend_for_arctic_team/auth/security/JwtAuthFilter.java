package com.arctic.backend_for_arctic_team.auth.security;

import com.arctic.backend_for_arctic_team.auth.service_implementation.authentication.TokenBlackListedService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenBlackListedService tokenBlackListedService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        final String requestPath = request.getServletPath();
        String method = request.getMethod();
        log.debug("Filtering request: {} {}", method, requestPath);
        if ((requestPath.startsWith("/api/v1/auth/") ||  requestPath.startsWith("/api/metrics/upload")) && !requestPath.equals("/api/v1/auth/logout") ||  requestPath.startsWith("/api/metrics/upload") ||  requestPath.startsWith("/api/v1/metrics/upload") ||  requestPath.startsWith("/swagger") ||  requestPath.startsWith("/v3/api-docs") || requestPath.startsWith("/actuator")) {
            log.debug("Пропускаю проверку токена для запроса: {} {}", method, requestPath);
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.debug("No token for: {}", requestPath);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        jwt = authHeader.substring(7);

        try {
            if (tokenBlackListedService.isAccessTokenBlacklisted(jwt)) {
                log.info("Пользователь вышел из аккаунта");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            if (!requestPath.equals("/api/v1/auth/refresh") && jwtUtil.isRefresh(jwt)) {
                log.debug("Отправлен refresh token, instead of access");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            username = jwtUtil.extractUsername(jwt);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.debug("Аутентификация отсутствует, проверяем токен для пользователя: {}", username);
                if (jwtUtil.validateToken(jwt)) {
                    log.debug("Token valid {}", username);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.debug("Установлена аутентификация для пользователя: {}", username);
                }
            } else {
                if (username == null) {
                    log.warn("Не удалось извлечь username из токена");
                } else {
                    log.debug("Аутентификация уже установлена для пользователя: {}", username);
                }
            }
        } catch (Exception e) {
            logger.error("JWT аутентификация неудачная" + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);

    }
}



