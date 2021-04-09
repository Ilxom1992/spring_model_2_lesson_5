package uz.pdp.appjwtrealemailauditing.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appjwtrealemailauditing.entity.User;
import uz.pdp.appjwtrealemailauditing.entity.enams.RoleEnum;
import uz.pdp.appjwtrealemailauditing.payload.ApiResponse;
import uz.pdp.appjwtrealemailauditing.payload.RegisterDto;
import uz.pdp.appjwtrealemailauditing.repository.RoleRepository;
import uz.pdp.appjwtrealemailauditing.repository.UserRepository;
import uz.pdp.appjwtrealemailauditing.security.SecurityConfig;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    final UserRepository userRepository;
    final SecurityConfig securityConfig;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;
    final JavaMailSender javaMailSender;
    public AuthService(UserRepository userRepository, SecurityConfig securityConfig, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JavaMailSender javaMailSender) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.javaMailSender = javaMailSender;
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
    public Boolean sendEmail(String sendingEmail,String emailCod) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com");
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
}
