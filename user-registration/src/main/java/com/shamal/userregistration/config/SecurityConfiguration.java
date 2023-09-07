package com.shamal.userregistration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SuccessHandler successHandler(){
        return new SuccessHandler();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,UserDetailsService userDetailsService) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
                                .requestMatchers("/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .successHandler(successHandler())
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID"))
                .rememberMe(rememberMe ->
                        rememberMe
                                .rememberMeServices(rememberMeServices(userDetailsService))
                                .key("Token")
                );

        return http.build();
    }
    @Bean
    public RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices("Token", userDetailsService);
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/img/**");
    }
//    @Bean
//    UserDetailsService getUserDetailsService() {
//        return new CustomUserDetailsService();
//    }
//    @Bean
//    BCryptPasswordEncoder getPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    @Bean
//    DaoAuthenticationProvider getDaoAuthProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
//        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
//        return daoAuthenticationProvider;
//    }
}
