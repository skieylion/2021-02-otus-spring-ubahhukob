package spring.homework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import spring.homework.domain.Permission;
import spring.homework.domain.Role;
import spring.homework.services.ServiceUserDetailsImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService serviceUserDetails;

    public SecurityConfig(UserDetailsService serviceUserDetails) {
        this.serviceUserDetails = serviceUserDetails;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET,"/book/*").hasAuthority(Permission.READ.get())
                .antMatchers(HttpMethod.DELETE,"/book/*").hasAuthority(Permission.WRITE.get())
                .antMatchers(HttpMethod.PUT,"/book").hasAuthority(Permission.WRITE.get())
                .antMatchers(HttpMethod.POST,"/book").hasAuthority(Permission.WRITE.get())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/index");
    }

    /*@Bean
    @Override
    public UserDetailsService userDetailsService(){
        return new InMemoryUserDetailsManager(
                User.builder()
                    .username("admin")
                    .password(passwordEncoder().encode("admin"))
                    .authorities(Role.ADMIN.getAuthorities())
                    .build(),
                User.builder()
                    .username("user")
                    .password(passwordEncoder().encode("user"))
                    .authorities(Role.USER.getAuthorities())
                    .build()
        );
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder(4);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(serviceUserDetails);
        return daoAuthenticationProvider;
    }

}
