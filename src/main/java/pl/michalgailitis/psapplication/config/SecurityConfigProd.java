package pl.michalgailitis.psapplication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.michalgailitis.psapplication.controllers.WebConstants;
import pl.michalgailitis.psapplication.services.users.CustomUserDetailsService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Profile("prod")
public class SecurityConfigProd extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(ConfigConsts.CREATE_USER_URL).permitAll()
                .antMatchers(HttpMethod.GET, WebConstants.USERS_URL).hasAuthority(ConfigConsts.ADMIN_ROLE)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(ConfigConsts.LOGIN_URL).permitAll()
                .defaultSuccessUrl(ConfigConsts.KEYWORD_URL, true)
                .and()
                .logout().logoutUrl(ConfigConsts.LOGOUT_URL).logoutSuccessUrl(ConfigConsts.LOGOUT_VIEW_URL)
                .and()
                .rememberMe().key(ConfigConsts.REMEMBER_ME_COOKIE_NAME).tokenValiditySeconds(10)
                .and()
                .httpBasic()
                .and().csrf().disable()
                .headers().frameOptions().disable();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}