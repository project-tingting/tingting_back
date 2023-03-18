package com.date.tingting.config.security;

import com.date.tingting.config.jwt.JwtAuthenticationFilter;
import com.date.tingting.config.jwt.JwtExceptionFilter;
import com.date.tingting.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    private final JwtExceptionFilter jwtExceptionFilter;

    @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic().disable()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/tingting/regenerateToken","/regenerateToken").permitAll()
                    .antMatchers("/tingting/user").hasRole("USER")
                    .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);
                    http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);
    }

        // 암호화에 필요한 PasswordEncoder Bean 등록
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
    }
}
