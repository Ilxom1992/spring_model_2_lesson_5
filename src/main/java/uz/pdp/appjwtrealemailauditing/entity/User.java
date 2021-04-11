package uz.pdp.appjwtrealemailauditing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")

public class User implements UserDetails  {
    @Id
    @GeneratedValue
    private UUID id;//USRNING TAKRORLANMAS RAQQAMI
    @Size(min = 3,max = 50)
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private  String phoneNumber;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private  Timestamp updateAt;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> role;
       private boolean accountNonExpired=true;//bu acountning amal qilish muddati
       private boolean accountNonLocked=true;//bu user boloclanmaganligi
       private boolean credentialsNonExpired=true;//
       private boolean enabled;//


    private  String emailCode;
//BU USER DETAILESNINIG METHODLARI
    //USERNINIG HUQUQLARI
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role;
    }
//USERNINIG USER NAMENI QAYTARUVCHI METOD
    @Override
    public String getUsername() {
        return this.email;
    }
//BU ACCOUNTNINIG AMAL QILISH MUDDATI NI QAYTARADI
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }
//ACCOUNT BLOCLANMAGANLIGI HOLATINI QAYTARADI
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

//ACCOUNTNINIG O'CHIQ YOKI YONIQLIGINI QAYTARADI
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
