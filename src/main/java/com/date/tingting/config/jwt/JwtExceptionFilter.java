package com.date.tingting.config.jwt;


import com.date.tingting.handler.exception.JwtException;
import com.date.tingting.handler.response.ErrorResponse;
import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    final static Gson gson = new Gson();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, res); // go to 'JwtAuthenticationFilter'
        } catch (ExpiredJwtException ex) {
            setErrorResponse(HttpStatus.UNAUTHORIZED, res, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex) throws IOException {
        res.setStatus(status.value());
        res.setContentType("application/json; charset=UTF-8");

        ErrorResponse response = ErrorResponse.builder()
                .code("402")
                .message("만료된 토큰입니다.")
                .build();

        res.getWriter().write(gson.toJson(response));
    }

}