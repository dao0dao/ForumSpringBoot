package com.post_hub.iam_service.security.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.post_hub.iam_service.security.JwtTokenProvider;
import com.post_hub.iam_service.security.model.CustomUserDetails;
import com.post_hub.iam_service.security.model.constans.SecurityConstans;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader(SecurityConstans.AUTHORIZATION_HEADER);

        if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstans.BEARER_PREFIX)) {
            String token = authorizationHeader.substring(SecurityConstans.BEARER_PREFIX.length());

            if (this.jwtTokenProvider.isValidToken(token)) {
                String userEmail = this.jwtTokenProvider.getUserEmail(token);

                var roles = this.jwtTokenProvider.getUserRoles(token);
                UserDetails userDetails = new CustomUserDetails(userEmail, roles);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }
        filterChain.doFilter(request, response);
    }

}
