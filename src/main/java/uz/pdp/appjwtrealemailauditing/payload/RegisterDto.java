package uz.pdp.appjwtrealemailauditing.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterDto {
    @Size(min = 3,max = 50)
    private String firstName;
    @Size(min = 3,max = 50)
    @Column(nullable = false)
    private String lastName;
    @Email
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    @NonNull
    private String password;

}
