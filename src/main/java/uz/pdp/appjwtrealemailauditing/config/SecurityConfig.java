package uz.pdp.appjwtrealemailauditing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.pdp.appjwtrealemailauditing.service.AuthService;

import java.util.Properties;
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }
    //USERGA TOKIN YASAB QAYTARISH UCHUN ISHLATILADIGAN METHOD

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http
               .csrf().disable()
               .httpBasic().disable()
               .authorizeRequests()
               .antMatchers("/api/auth/register",
                                       "/api/auth/verifyEmail",
                                       "/api/auth/login")
               .permitAll()
               .anyRequest()
               .authenticated();

    }




    @Bean
PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}



    @Bean
public JavaMailSender javaMailSender(){
    JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
    mailSender.setHost("smtp.gmail.com");
    mailSender.setPort(587);
    mailSender.setUsername("ilxom.xojamurodovg@mail.com");
    mailSender.setPassword("qoramarmarid");
    Properties properties =mailSender.getJavaMailProperties();
    properties.put("mail.transport.protocol","smtp");
    properties.put("mail.smtp.auth","true");
    properties.put("mail.smtp.starttls.enable","true");
    properties.put("mail.debug","true");
    return  mailSender;
}
}
