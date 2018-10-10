package com.mdev.amanager.web.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by gmilazzo on 28/09/2018.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @SuppressWarnings("deprecation")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().disable().
                authorizeRequests().
                antMatchers("/pages/secure/**").access("hasRole('ROLE_ADMIN')").
                and().formLogin().  //login configuration
                loginPage("/pages/common/login.jsf").
                loginProcessingUrl("/j_spring_security_login").
                usernameParameter("j_username").
                passwordParameter("j_password").
                defaultSuccessUrl("/pages/secure/subscriber/subscriber.search.jsf").
                failureUrl("/pages/common/login.jsf?error=true").
                and().logout().    //logout configuration
                logoutUrl("/j_spring_security_logout").
                logoutSuccessUrl("/pages/common/login.jsf");

    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);

//        auth.inMemoryAuthentication().withUser("test").password("test").roles("ADMIN");
    }
}
