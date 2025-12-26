package com.post_hub.refreshing_knowledge_of_SpringBoot.security.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.post_hub.refreshing_knowledge_of_SpringBoot.security.JwtTokenProvider;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.model.CustomUserDetailsService;
import com.post_hub.refreshing_knowledge_of_SpringBoot.security.model.constans.SecurityConstans;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        var cookies = request.getCookies();

        if(cookies == null){
            filterChain.doFilter(request, response);
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            for (Cookie cookie : cookies) {
                if (SecurityConstans.JWT_COOKIE_NAME.equals(cookie.getName())) {
                    var token = cookie.getValue();
                    if (this.jwtTokenProvider.isValidToken(token)) {
                        String userEmail = this.jwtTokenProvider.getUserEmail(token);

                        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userEmail);

                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        }
        filterChain.doFilter(request, response);
    }

}
