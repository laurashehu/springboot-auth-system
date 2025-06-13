package com.questify.backend.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JWTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public JwtTokenFilter(JWTokenProvider tokenProvider, CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        logger.info("Authorization header: " + header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            logger.info("Extracted JWT token: " + token);

            if (tokenProvider.validateToken(token)) {
                String email = tokenProvider.getEmailFromJwt(token);
                logger.info("Email from token: " + email);

                UserDetails userDetails = userDetailsService.loadUserByUsername(email);



                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    logger.info("Authentication set for user: " + email);
                } else {
                    logger.warn("UserDetails is null for email: " + email);
                }
            } else {
                logger.warn("JWT token is invalid");
            }
        } else {
            logger.info("No Bearer token found in header");
        }

        filterChain.doFilter(request, response);
    }
}
