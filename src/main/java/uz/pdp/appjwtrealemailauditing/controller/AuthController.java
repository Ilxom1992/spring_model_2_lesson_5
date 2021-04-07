package uz.pdp.appjwtrealemailauditing.controller;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.appjwtrealemailauditing.payload.RegisterDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody RegisterDto registerDto){
return null;
    }
}
