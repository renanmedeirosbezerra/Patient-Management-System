package com.pm.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    //Aqui será configurado todas as regras de segurança da aplicação


    /*
    Permite todas as requisições sem exigir autenticação, ou seja, qualquer endpoint da API estará acessível publicamente
    Esse tipo de configuração é mais comum em ambientes de desenvolvimento ou APIs públicas
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable);
        //CSRF (Cross-Site Request Forgery)

        return http.build();
    }

    /*
    PasswordEncoder é uma interface do Spring Security usada para criptografar senhas antes de armazená-las no DB
    Serve também para comparar senhas criptografadas durante a autenticação
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
