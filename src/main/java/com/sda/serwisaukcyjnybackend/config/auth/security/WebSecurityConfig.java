package com.sda.serwisaukcyjnybackend.config.auth.security;

import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new SAAuthenticationProvider());
    }

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auctions/bid").authenticated()
                .antMatchers(HttpMethod.POST, "/api/auctions/*/images").authenticated()
                .antMatchers("/api/auth/edit-user").authenticated()
                .antMatchers("/api/pay/buy-premium-link").authenticated()
                .antMatchers("/api/auth/edit-user/*").authenticated()
                .antMatchers(HttpMethod.POST, "/api/auth/logout").authenticated()
                .antMatchers(HttpMethod.POST, "/api/auctions/*/buy-now").authenticated()
                .antMatchers(HttpMethod.POST, "/api/auth/resending-verification-code").authenticated()
                .antMatchers("/api/auctions/bidded").authenticated()
                .antMatchers("/api/auctions/observed").authenticated()
                .antMatchers("/api/auctions/owned").authenticated()
                .antMatchers("/api/auctions/create-auction-data").authenticated()
                .anyRequest().permitAll()
                .and().csrf().disable().cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
