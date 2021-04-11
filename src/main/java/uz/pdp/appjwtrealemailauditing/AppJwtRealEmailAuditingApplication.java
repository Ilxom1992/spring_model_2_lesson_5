package uz.pdp.appjwtrealemailauditing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppJwtRealEmailAuditingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppJwtRealEmailAuditingApplication.class, args);
    }

}

//        Pul o'tkazmalarini amalga oshiruvchi dastur tuzing.
//        User sistemaga authentication orqali kirib, o'ziga tegishli CARD orqali boshqa
//        CARD ga pul transfer qilish jarayonini amalga oshirsin.
//        Transfer jarayonida CARD da o'tkazilayotgan va transfer uchun kommisiya miqdoridagi
//        mablag' yetarli ekanligi va CARD shu user ga tegishli ekanligi tekshirilsin.
//        Kartadagi kirimlarni va chiqimlarni alohida yozib boring.
//        Foydalanuvchi o'ziga tegishli card tarixini (output, income) ko'rsatuvchi method yozing.