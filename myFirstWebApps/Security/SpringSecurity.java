package com.learnSpringBoot.myFirstWebApps.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

@SuppressWarnings("removal")
@Configuration
public class SpringSecurity {

    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager(){
        Function<String,String> passwordEncoder =
                input -> passwordEncoder().encode(input);
        UserDetails userDetails = getDetails(passwordEncoder, "sarkar", "12345");
        UserDetails userDetails2 = getDetails(passwordEncoder, "shubhodeep", "123456");
        return new InMemoryUserDetailsManager(userDetails,userDetails2);
    }

    private static UserDetails getDetails(Function<String, String> passwordEncoder, String username, String password) {
        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER","ADMIN")
                .build();
        return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth -> auth.anyRequest().authenticated()
        );
        http.formLogin(Customizer.withDefaults());
        http.csrf().disable();
        http.headers().frameOptions().disable();
        return http.build();
    }
}
