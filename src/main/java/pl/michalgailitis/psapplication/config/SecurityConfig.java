package pl.michalgailitis.psapplication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2/**").permitAll()
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

    //MB logowanie przez uzytkownikow - nadpisac configure


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("asd").password().roles();
//    }
}
