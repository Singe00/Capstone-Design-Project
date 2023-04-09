package com.example.CrtDgn;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/signUp","/logout", "/oauth/**", "/css/**", "/images/**", "/js/**", "/h2-console/**")
                .permitAll() // "/"와 "/login", "/signUp", "/oauth/**", "/css/**", "/images/**", "/js/**", "/h2-console/**"에 대한 접근을 모두 허용
                .anyRequest().authenticated() // 그 외의 요청에 대해서는 인증이 필요
                .and()
                .csrf().disable(); // CSRF 보호 비활성화 (H2 콘솔 접근을 위해)
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"));
    }
}