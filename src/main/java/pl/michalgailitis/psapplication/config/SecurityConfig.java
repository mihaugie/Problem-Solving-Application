package pl.michalgailitis.psapplication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.michalgailitis.psapplication.services.users.CustomUserDetailsService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/users/create/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                .anyRequest().authenticated()
//                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
//                .loginProcessingUrl("/appLogin")
                .defaultSuccessUrl("/?keyword=", true)
//                .failureUrl("/loginPage")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/logoutView")
                .and()
                .rememberMe().key("nazwa-ciasteczka").tokenValiditySeconds(10)  //CW 7 - ZAPAMIĘTYWANIE ZALOGOWANEGO UZYTKOWNIKA
                .and()
                .httpBasic()
                .and()
                .csrf().ignoringAntMatchers("/h2/**")
                .and().csrf().disable()
                .headers().frameOptions().disable();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}