package food_delivery_app_backend.Config;

import food_delivery_app_backend.Enum.GlobalRole;
import food_delivery_app_backend.Jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class UserConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter)throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->auth
                        .requestMatchers(

                                //user register flow
                                "/user/login",
                                "/user/register",
                                "/user/account-verification",
                                "/user/password-reset",
                                "/user/request-otp",

                                //owner register flow
                                "/owner/register",
                                "/owner/account-verification",
                                "/owner/login",
                                "/owner/request-otp",
                                "/owner/reset-password",

                                //delivery-partner register flow
                                "/delivery/partner/register",
                                "/delivery/partner/account-verification",
                                "/delivery/partner/login",
                                "/delivery/partner/request-otp",
                                "/delivery/partner/reset-password",



                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // User protected endpoints
                        .requestMatchers("/user/**").hasRole("USER")

                        // Business (owner) protected
                        .requestMatchers("/business/partner/**").hasRole("OWNER")

                        // Delivery partner protected
                        .requestMatchers("/delivery/partner/**").hasRole("DELIVERY_PARTNER")

                        // Order accessible by all role types
                        .requestMatchers("/order/**").hasAnyRole("USER", "OWNER", "DELIVERY_PARTNER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
