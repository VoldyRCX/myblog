package com.voldy.myblog.config;

import com.voldy.myblog.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * 权限控制设置
 * TODO
 * @author 任程显--Voldy--
 * @version 0.0.1
 * @since 2019/5/6
 **/
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法防护设置
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String KEY = "Voldy";

    @Resource
    private UserService userService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder); //加密方式
        return authenticationProvider;
    }
    /**
     * l拦截器配置
     * @param httpSecurity
     * @throws Exception
     */
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index" ).permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN") // 需要相应的角色才能访问

                .and()
                .formLogin()   //基于 Form 表单登录验证
                .loginPage("/login").failureUrl("/login-error") // 自定义登录界面
                .and().rememberMe().key(KEY) // 启用 remember me
                .and().exceptionHandling().accessDeniedPage("/403");  // 处理异常，拒绝访问就重定向到 403 页面
        //跨站访问防护设置
        httpSecurity.csrf().ignoringAntMatchers("/*/avatar/**", "/u/*/blogs/edit/**", "/u/*/blogs/**");
        httpSecurity.headers().frameOptions().sameOrigin();
    }

    /**
     * 认证信息管理
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        auth.authenticationProvider(authenticationProvider());
    }
}
