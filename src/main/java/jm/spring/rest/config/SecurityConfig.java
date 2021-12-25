package jm.spring.rest.config;

import jm.spring.rest.config.handler.LoginSuccessHandler;
import jm.spring.rest.entity.Roles;
import jm.spring.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //АУТЕНТИФИКАЦИЯ - идентификация реальности пользователя
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //аутентификация выполняется через класс с интерфейсом UserDetailsService
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    //АВТОРИЗАЦИЯ - идентификация доступа к ресурсам в зависимости от роли пользователя
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .successHandler(new LoginSuccessHandler())
            .permitAll();

        http.logout()
            .permitAll()
            .and().csrf().disable();

        http
            .authorizeRequests() // предоставить разрешение для перечисленых URL
            .antMatchers("/login").anonymous()
            .antMatchers("/user/**").hasRole(Roles.USER.toString()) //hasRole("USER")
            .antMatchers("/admin/**").hasRole(Roles.ADMIN.toString()) //hasAuthority("ROLE_ADMIN")
            .antMatchers("/api/**").permitAll()//.hasRole(Roles.ADMIN.toString()) //hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated()   //любые запросы только аутентифицированным пользователям
            .and().formLogin().permitAll(); //к логину доступ всем
    }
}
