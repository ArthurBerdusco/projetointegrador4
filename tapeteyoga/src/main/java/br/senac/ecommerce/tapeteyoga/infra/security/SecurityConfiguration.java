package br.senac.ecommerce.tapeteyoga.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import br.senac.ecommerce.tapeteyoga.service.MyUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    MyUserDetailService myUserRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/home","/setup", "/backoffice/cadastrar-usuario").permitAll();
                    registry.requestMatchers("/", "/img/**", "/css/**", "/js/**").permitAll();
                    registry.requestMatchers("/backoffice/**").hasRole("ADMIN");
                    registry.requestMatchers("/user/**").hasRole("USER");
                    registry.anyRequest().authenticated();
                })
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .logoutSuccessUrl("/login/backoffice") // Página para redirecionar após o logout
                                .logoutUrl("/logout") // Endpoint de logout
                                .permitAll()
                )
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login/backoffice")
                            .successHandler(new AuthenticationSuccessHandler())
                            .permitAll();
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return myUserRepository;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserRepository);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
