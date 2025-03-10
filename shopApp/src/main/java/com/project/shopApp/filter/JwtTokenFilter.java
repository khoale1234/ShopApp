package com.project.shopApp.filter;

import com.project.shopApp.components.JwtTokenUtil;
import com.project.shopApp.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
@Value("${api.prefix}")
private String apiPrefix;
private final UserDetailsService userDetailsService;
private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
    try {
        if(this.isBypassToken(request)){
            filterChain.doFilter(request, response);
        }
        final String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader==null && !authorizationHeader.startsWith("Bearer ")){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        }
            final String token = authorizationHeader.substring(7);
            final String phoneNumber=jwtTokenUtil.extractPhoneNumber(token);
            if(phoneNumber!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                filterChain.doFilter(request, response);
            }

    }
    catch (Exception e) {
        ResponseEntity.badRequest().body(e.getMessage());
    }

    }
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("/%s/roles",apiPrefix), "GET"),
                Pair.of(String.format("/%s/products",apiPrefix), "GET"),
                Pair.of(String.format("/%s/categories",apiPrefix), "GET"),
                Pair.of(String.format("/%s/users/register",apiPrefix), "POST"),
                Pair.of(String.format("/%s/users/login",apiPrefix), "POST"),
                 Pair.of(String.format("/%s/orders",apiPrefix), "GET"));
        for (Pair<String, String> bypassToken : bypassTokens) {
            if (request.getServletPath().contains(bypassToken.getLeft()) && request.getMethod().contains(bypassToken.getRight())) {
               return true;
            }
        }
        return false;
    }
}
