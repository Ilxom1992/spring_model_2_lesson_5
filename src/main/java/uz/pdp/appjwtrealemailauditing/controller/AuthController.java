package uz.pdp.appjwtrealemailauditing.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjwtrealemailauditing.payload.ApiResponse;
import uz.pdp.appjwtrealemailauditing.payload.RegisterDto;
import uz.pdp.appjwtrealemailauditing.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @GetMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto){
        ApiResponse userRegister = authService.userRegister(registerDto);
        return ResponseEntity.status(userRegister.isSuccess() ? 201 : 409 ).body(userRegister);
    }
    @GetMapping("/verifyEmail")
    public  HttpEntity<?> verifyEmail(@RequestParam String emailCode,@RequestParam String email){
     ApiResponse apiResponse=  authService.verifyEmail(emailCode,email);
     return  ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
