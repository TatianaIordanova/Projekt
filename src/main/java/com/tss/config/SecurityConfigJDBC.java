package com.tss.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * It's a Spring Security configuration class that sets up JDBC-based authentication. 
 * It defines a data source for authentication(from application.properties file), specifies password encoding, and configures HTTP security for the application.
 * This includes defining access rules, login and logout behavior, exception handling, and ensuring all requests are made over HTTPS(certificate configuration is in application.properties file). 
 */
@EnableWebSecurity
@Configuration
public class SecurityConfigJDBC {

    @Autowired
    private Environment env;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        DataSource dataSourceAuth = this.createDataSource();
        
        auth.jdbcAuthentication().dataSource(dataSourceAuth)
                .usersByUsernameQuery("select user_name, user_pass_crypt, enabled from users where user_name=?")
                .authoritiesByUsernameQuery("select user_name, role_name from user_roles where user_name=?")
                .passwordEncoder(passwordEncoder);
    }

    private DataSource createDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.authdatasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.authdatasource.url"));
        dataSource.setUsername(env.getProperty("spring.authdatasource.username"));
        dataSource.setPassword(env.getProperty("spring.authdatasource.password"));
        return dataSource;

    }
    
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers( "/cars").hasAnyRole("USER", "ADMIN")
                                .antMatchers("/cars/**").hasRole("ADMIN")
                                .antMatchers("/" ).authenticated()
//                                .antMatchers("/",  "/public/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .permitAll()
                                .defaultSuccessUrl("/", true)
                )
                .logout(logout ->
                        logout.permitAll()
                )
                
                .exceptionHandling(exceptionHandling ->
                    exceptionHandling.accessDeniedPage("/access-denied")
                )
                .requiresChannel().anyRequest().requiresSecure();

        return http.build();
    }
}
