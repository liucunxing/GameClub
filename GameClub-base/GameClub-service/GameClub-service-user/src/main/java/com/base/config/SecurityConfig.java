package com.base.config;

import com.base.filter.JwtAuthenticationTokenFilter;
import com.base.service.Impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginFailureHandler loginFailureHandler;
    @Autowired
    private MyUserDetailsServiceImpl myUserDetailsService;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private static final String URL_WHITRLIST[] = {
            "/api/user/login", "/login", "/logout", "/css/**", "/js/**", "/index.html", "favicon.ico", "/doc.html",
            "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**", "/swagger-ui.html", "configuration/ui","/api/user/getPerms"
    };
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启跨域  以及csrf攻击 关闭
        http
        .cors()
        .and()
        .csrf()
        .disable()
        //登陆登出配置
        .formLogin()
        .successHandler(loginSuccessHandler).failureHandler(loginFailureHandler)
        //session禁用
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //拦截规则
        .and()
        .authorizeRequests()
        .antMatchers(URL_WHITRLIST)
        .permitAll()
        .anyRequest()
        .authenticated();
                //异常处理设置
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
