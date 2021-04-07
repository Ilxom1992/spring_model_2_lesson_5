package uz.pdp.appjwtrealemailauditing.service;

import org.springframework.stereotype.Service;
import uz.pdp.appjwtrealemailauditing.entity.User;
import uz.pdp.appjwtrealemailauditing.payload.ApiResponse;
import uz.pdp.appjwtrealemailauditing.payload.RegisterDto;
import uz.pdp.appjwtrealemailauditing.repository.UserRepository;

@Service
public class AuthService {
    final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ApiResponse userRegister(RegisterDto registerDto){
        boolean existsByEmail = userRepository.existsByEmail(registerDto.getEmail());
        if (existsByEmail){
            return new ApiResponse("Bunday email bazada mavjud",false);
        }
        User user=new User();
        return null;
    }



}
