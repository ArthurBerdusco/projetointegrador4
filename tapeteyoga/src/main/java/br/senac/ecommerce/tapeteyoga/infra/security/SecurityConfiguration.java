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
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

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

                    /* Todos */
                    registry.requestMatchers("/setup", "/img/**", "/css/**", "/backoffice/setup", "/").permitAll();

                    /* Administrador e Estoquista */
                    registry.requestMatchers("/backoffice/").hasAnyRole("Administrador", "Estoquista");

                    /* Estoquista ---> NÃO PODE ACESSAR /backoffice/listar-produtos */
                    registry.requestMatchers("/backoffice/usuarios").hasRole("Administrador");

                    registry.anyRequest().authenticated();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/backoffice")
                            .usernameParameter("email")
                            .successHandler(new SimpleUrlAuthenticationSuccessHandler("/backoffice/home"))
                            .permitAll();
                })
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutSuccessUrl("/backoffice")
                        .logoutUrl("/logout")
                        .permitAll())
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
