package uz.pdp.appjwtrealemailauditing.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Properties;
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

       http
               .csrf().disable()
               .httpBasic().disable()
               .authorizeRequests()
               .antMatchers("/api/auth/register","/api/auth/register/verifyEmail").permitAll()
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
    mailSender.setUsername("ilxom.xojamurodov@gmail.com");
    mailSender.setPassword("qoramarmarid");
    Properties properties =mailSender.getJavaMailProperties();
    properties.put("mail.transport.protocol","smtp");
    properties.put("mail.smtp.auth","true");
    properties.put("mail.smtp.starttls.enable","true");
    properties.put("mail.debug","true");
    return  mailSender;
}
}
