package com.example.CrtDgn.Security;

import com.example.CrtDgn.Security.Jwt.JwtAuthenticationFilter;
import com.example.CrtDgn.Security.Jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    //암호화에 필요한 PasswordEncoder Bean 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //authenticationManager Bean 등록
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login","/join","/login2", "/signUp","/logout","/find","/change","/recommand", "/oauth/**","/main","/search/**","/charge/**","/interest/**", "/css/**", "/images/**", "/js/**", "/h2-console/**")
                .permitAll() // "/"와 "/login", "/signUp", "/oauth/**", "/css/**", "/images/**", "/js/**", "/h2-console/**"에 대한 접근을 모두 허용
                .anyRequest().authenticated() // 그 외의 요청에 대해서는 인증이 필요
                .and()
                // JwtAuthenticationFilter를 먼저 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable() // CSRF 보호 비활성화 (H2 콘솔 접근을 위해)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS); // 세션 생성 정책 설정

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"));
    }
}

//기본 세션 세큐리티 코드
/*
package com.example.CrtDgn.Security;

        import org.springframework.context.annotation.Configuration;
        import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.config.http.SessionCreationPolicy;
        import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/login", "/signUp","/logout", "/oauth/**","/main","/search/**","/charge/**","/interest/**", "/css/**", "/images/**", "/js/**", "/h2-console/**")
                .permitAll() // "/"와 "/login", "/signUp", "/oauth/**", "/css/**", "/images/**", "/js/**", "/h2-console/**"에 대한 접근을 모두 허용
                .anyRequest().authenticated() // 그 외의 요청에 대해서는 인증이 필요
                .and()
                .csrf().disable() // CSRF 보호 비활성화 (H2 콘솔 접근을 위해)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS); // 세션 생성 정책 설정

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"));
    }
}*/
