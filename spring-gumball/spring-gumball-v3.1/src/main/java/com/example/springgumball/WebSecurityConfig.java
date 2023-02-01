package com.example.springgumball;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    /*

        Autoconfiguring Spring Security

        REF:  Securing Spring | Spring in Action, Sixth Edition

        What does this barebones security configuration do for you? Not much, actually.
        The main thing it does is declare a PasswordEncoder bean, which we’ll use both
        when creating new users and when authenticating users at login.

        In this case, we’re using BCryptPasswordEncoder, one of a handful of password
        encoders provided by Spring Security, including the following:

            * BCryptPasswordEncoder—Applies bcrypt strong hashing encryption
            * NoOpPasswordEncoder—Applies no encoding
            * Pbkdf2PasswordEncoder—Applies PBKDF2 encryption
            * SCryptPasswordEncoder—Applies Scrypt hashing encryption
            * StandardPasswordEncoder—Applies SHA-256 hashing encryption


    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /*

        * https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html
        * https://docs.spring.io/spring-boot/docs/current/reference/html/cli.html

        Spring Security’s InMemoryUserDetailsManager implements UserDetailsService to provide
        support for username/password based authentication that is stored in memory.
        InMemoryUserDetailsManager provides management of UserDetails by implementing
        the UserDetailsManager interface. UserDetails based authentication is used by
        Spring Security when it is configured to accept a username/password for authentication.

        In this sample we use Spring Boot CLI to encode the password of password and get the
        encoded password of {bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW.

        Spring Boot CLI:  spring encodepassword password
        Alternatively:  https://bcrypt.online/

     */
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("$2y$10$HfdK56jb5Ut1fBpZq/lkFuPqiKdMOZBJ8H96.fx6dyYrEkI8dmPD6")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }


    /*
        References:
            * https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/form.html
            * https://stackoverflow.com/questions/41373588/spring-security-configuration-for-post-request
            * https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable(); // disable CSRF for POSTS
        http.headers().frameOptions().sameOrigin();
        SecurityFilterChain ret = http
                .formLogin(withDefaults())
                .authorizeRequests()
                .antMatchers("/gumball").hasRole("USER")
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/").permitAll()
                .and()
                .build();
        return ret;
    }

}
