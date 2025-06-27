package com.example.taskmanager.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    .csrf().disable()	
	            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/tasks/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/tasks/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/tasks/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/tasks/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
	}

	
	@Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withDefaultPasswordEncoder()
            .username("Mega")
            .password("mega@123")
            .roles("USER")
            .build();

        UserDetails user2 = User.withDefaultPasswordEncoder()
            .username("jaga")
            .password("jaga@123")
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user1, user2);
	}

}