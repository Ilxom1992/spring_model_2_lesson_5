package uz.pdp.appjwtrealemailauditing.service;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.pdp.appjwtrealemailauditing.entity.User;
import uz.pdp.appjwtrealemailauditing.entity.enams.RoleEnum;
import uz.pdp.appjwtrealemailauditing.payload.ApiResponse;
import uz.pdp.appjwtrealemailauditing.payload.LoginDto;
import uz.pdp.appjwtrealemailauditing.payload.RegisterDto;
import uz.pdp.appjwtrealemailauditing.repository.RoleRepository;
import uz.pdp.appjwtrealemailauditing.repository.UserRepository;
import uz.pdp.appjwtrealemailauditing.config.SecurityConfig;
import uz.pdp.appjwtrealemailauditing.security.JwtProvider;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.prefs.BackingStoreException;

@Service
public class AuthService implements UserDetailsService {
    final UserRepository userRepository;
    final SecurityConfig securityConfig;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;
    final JavaMailSender javaMailSender;
    final AuthenticationManager authenticationManager;
    final JwtProvider jwtProvider;
    public AuthService(UserRepository userRepository, SecurityConfig securityConfig, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JavaMailSender javaMailSender, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    public ApiResponse userRegister(RegisterDto registerDto){
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail){
            return new ApiResponse("Bunday email bazada mavjud",false);
  }
        User user=new User();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Collections.singleton(roleRepository.findByRoleName(RoleEnum.ROLE_USER)));

        //tasodifiy sonni yaratib beradi va userga saqlanadi

        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        //EMAILGA CHAQIRISH METHODINI CHAQIRYABMIZ
        sendEmail(user.getEmail(),user.getEmailCode());
        return new ApiResponse("Muafaqiyatli ro'yhatdan o'tdingiz Aakkonutingiz " +
                                       "aktivlashtirishingiz uchun emailni tasdiqlang",true);
    }
    public Boolean sendEmail(String sendingEmail, String emailCod) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("ilxom.xojamurodov@gmail.com");
            mailMessage.setTo(sendingEmail);
              mailMessage.setSubject("Akkountni Tasdiqlash");
            mailMessage.setText("<a href='http://localhost:8081/api/auth/verifyEmail?Code=" + emailCod + "&email=" + sendingEmail + "'>Tasdiqlang</a>");
            javaMailSender.send(mailMessage);
            return true;
        }
catch (Exception e){
            return false;
}

    }

    public ApiResponse verifyEmail(String emailCode, String email) {
        Optional<User> optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()){
            User user= optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Account tasdiqlandi",true);
        }
        return new ApiResponse("Akkount alloqachon tasdiqlangan",false);
    }
//    public ApiResponse login(LoginDto loginDto) {
//        try {
//            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    loginDto.getUsername(),
//                    loginDto.getPassword()));
//            User user=(User) authenticate.getPrincipal();
//            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRole());
//            return new ApiResponse("Token",true,token);
//
//        }catch (BadCredentialsException badCredentialsException){
//return new ApiResponse( "Parol yoki lagin hato",false);
//        }
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isPresent())
            return optionalUser.get();
        throw new UsernameNotFoundException(username+ " topilmadi");
        //IKKINCHI USULDA YOZISH
     //   return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username+ " topilmadi"));
    }
}
