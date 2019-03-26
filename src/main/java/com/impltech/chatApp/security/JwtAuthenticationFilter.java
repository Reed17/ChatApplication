package com.impltech.chatApp.security;

import com.impltech.chatApp.enums.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtProvider jwtProvider;
    private final UnauthorizedAuthenticationEntryPoint unauthorizedEntryPoint;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtProvider jwtProvider, UnauthorizedAuthenticationEntryPoint unauthorizedEntryPoint, UserDetailsServiceImpl userDetailsService) {
        this.jwtProvider = jwtProvider;
        this.unauthorizedEntryPoint = unauthorizedEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = jwtProvider.getTokenFromRequest(request);
        try {
            if (StringUtils.hasText(accessToken) && jwtProvider.validateAccessToken(accessToken)) {
                final Long userId = jwtProvider.getUserIdFromToken(accessToken);
                final UserDetails userDetails = userDetailsService.loadUserById(userId);
                final UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (AuthenticationException ex){
            LOG.error(Message.AUTHENTICATION_FAILED.getMessage());
            SecurityContextHolder.clearContext();
            unauthorizedEntryPoint.commence(request, response, ex);
        }
        filterChain.doFilter(request, response);
    }
}
