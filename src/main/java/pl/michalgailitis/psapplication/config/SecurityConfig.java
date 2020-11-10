package pl.michalgailitis.psapplication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.michalgailitis.psapplication.services.users.CustomUserDetailsService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")//.defaultSuccessUrl("/index", true).failureUrl("/login")
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/logoutView")
//                .and()
//                .rememberMe().key("nazwa-ciasteczka").tokenValiditySeconds(10)  //CW 7 - ZAPAMIĘTYWANIE ZALOGOWANEGO UZYTKOWNIKA
                .and()
                .httpBasic()
                .and()
                .logout()
                .and()
                .csrf().ignoringAntMatchers("/h2/**")
                .and().csrf().disable()
                .headers().frameOptions().disable();

    }

    @Override
    protected UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

    //MB logowanie przez uzytkownikow - nadpisac configure

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource();
//    }
}
