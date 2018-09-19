package com.htsc.cams.config;

import com.htsc.cams.security.filter.JWTAuthenticationFilter;
import com.htsc.cams.security.filter.JWTLoginFilter;
import com.htsc.cams.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * SpringSecurity的配置
 * 通过SpringSecurity的配置，将JWTLoginFilter，JWTAuthenticationFilter组合在一起
 * @author zhaoxinguo on 2017/9/13.
 */
//@Configuration
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 设置 HTTP 验证规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/user/signup", "/user/login").permitAll() // 所有 /users/signup 的POST请求 都放行
                // swagger start
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/*").permitAll()
                .antMatchers("/configuration/*").permitAll()
                // swagger end
                .anyRequest().authenticated()  // 所有请求需要身份认证
                .and()
                // 禁用Session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), JWTLoginFilter.class);
    }

    // 身份验证
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // userDetailsService 用过在项目中建立了，实际上就是用建立的那个
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        // 使用自定义身份验证组件
//        auth.authenticationProvider(new CustomAuthenticationProvider(userDetailsService,bCryptPasswordEncoder));
    }

}