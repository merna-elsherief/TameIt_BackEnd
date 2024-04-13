package com.pro.tameit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String[] PUBLIC_URLS = {
            "/auth/**", "/welcome","/auth/forgotPassword/**"
    };

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public JwtFilter authFilter() {
        return new JwtFilter();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBean());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(PUBLIC_URLS).permitAll().anyRequest()
                .authenticated()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
