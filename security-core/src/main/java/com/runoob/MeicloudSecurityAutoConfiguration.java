package com.runoob;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author DENGBIN
 * @since 2018-3-29
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(MeicloudSecurityProperties.class)
public class MeicloudSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {

    private final MeicloudSecurityProperties properties;

    @Autowired
    public MeicloudSecurityAutoConfiguration(MeicloudSecurityProperties properties) {
        this.properties = properties;
    }

    // @formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, properties.getBrowser().getLoginPage()).permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage(properties.getBrowser().getLoginPage())
                .loginProcessingUrl("/login")
                .and()
            .httpBasic();
    }
    // @formatter:on

    // @formatter:off
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("$2a$10$/YckwCeDk89no/pE7JzICOICEqWZ8uarS33usW/HM7hDrAka.ou1a").roles("USER")
                .and()
                .withUser("ADMIN").password("$2a$10$/YckwCeDk89no/pE7JzICOICEqWZ8uarS33usW/HM7hDrAka.ou1a").roles("ADMIN")
                .and()
            .passwordEncoder(new BCryptPasswordEncoder(10));
//        auth
//            .jdbcAuthentication()
//            .dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, true from users where username = ?")
//                .authoritiesByUsernameQuery("select username, 'ROLE_USER' from users where username = ?")
//            .passwordEncoder(new BCryptPasswordEncoder(10));
    }
    // @formatter:on
}
