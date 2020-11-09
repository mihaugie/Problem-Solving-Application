package pl.michalgailitis.psapplication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

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
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .withDefaultSchema()
//                .withUser(User.withUsername("user")
//                .password("pass")
//                        .roles("USER"));
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .dataSource();
//    }
}
