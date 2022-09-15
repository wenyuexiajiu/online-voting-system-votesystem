package com.votesystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * @author Ricardo
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //UserDetailsService身份认证
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //开启请求访问控制
        http.authorizeRequests()
                //放行静态资源
                .antMatchers("/login/**", "/noFound/**", "/main/**", "/common/send/email", "/common/doRegister", "/common/isRegister", "/common/register").permitAll()
                .antMatchers("/common/main", "/vote/**", "/admin/rankings", "/common/uploadHeadImg").hasAnyAuthority("admin", "audience", "judges", "singer")
                .antMatchers("/admin/**").hasAnyAuthority("admin")
                .antMatchers("/judge/**").hasAnyAuthority("judges", "admin")
                .anyRequest().authenticated()
                .and().csrf().disable();
        //开启用户登录配置
        http.formLogin()
                .loginPage("/common/login").permitAll()
                .loginProcessingUrl("/common/doLogin").permitAll()
                .defaultSuccessUrl("/common/index")
                .failureUrl("/common/errors")
                .usernameParameter("userAccount")
                .passwordParameter("userPassword");

        //开启退出配置
        http.logout()
                .logoutUrl("/common/logout")
                .logoutSuccessUrl("/common/login")
                .invalidateHttpSession(true);
        //开启记住我配置,并设置一周过期
        http.rememberMe()
                .rememberMeParameter("rememberme")
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                .tokenRepository(tokenRepository());
        //设置错误时响应的页面
        http.exceptionHandling()
                .accessDeniedPage("/noauth");
    }

    /**
     * 设置记住我的token存储
     * 持久化token到数据库中
     *
     * @return PersistentTokenRepository
     */
    @Bean
    public JdbcTokenRepositoryImpl tokenRepository() {
        JdbcTokenRepositoryImpl impl = new JdbcTokenRepositoryImpl();
        impl.setDataSource(dataSource);
//        impl.setCreateTableOnStartup(true);
        return impl;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
