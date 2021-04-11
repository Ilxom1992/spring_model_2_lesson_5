package uz.pdp.appjwtrealemailauditing.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.appjwtrealemailauditing.config.KimYozganiniBilish;
import uz.pdp.appjwtrealemailauditing.entity.Card;
import uz.pdp.appjwtrealemailauditing.entity.Outcome;
import uz.pdp.appjwtrealemailauditing.payload.ApiResponse;
import uz.pdp.appjwtrealemailauditing.payload.OutcomeDto;
import uz.pdp.appjwtrealemailauditing.repository.CardRepository;
import uz.pdp.appjwtrealemailauditing.repository.OutcomeRepository;
import uz.pdp.appjwtrealemailauditing.repository.UserRepository;

import java.util.UUID;

@Service
public class OutcomeService {
final CardRepository cardRepository;
final JavaMailSender javaMailSender;
final UserRepository userRepository;
final OutcomeRepository outcomeRepository;

    public OutcomeService(CardRepository cardRepository, JavaMailSender javaMailSender, UserRepository userRepository, OutcomeRepository outcomeRepository) {
        this.cardRepository = cardRepository;
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.outcomeRepository = outcomeRepository;
    }
KimYozganiniBilish userId=new KimYozganiniBilish();
    //  SEND MONEY

    /**
     * o'ziga tegishli CARD orqali boshqa
     * CARD ga pul transfer qilish jarayonini amalga oshirish.
     * @param outcomeDto
     * @return
     */
//
    public ApiResponse sendMoney(OutcomeDto outcomeDto){
      //  CARD shu user ga tegishli ekanligi tekshirish.
        Card card = cardRepository.findByNumberAndUserId(outcomeDto.getFromCardId(), userId.getCurrentAuditor().get());
        if (card.isActive()){
          //  Transfer jarayonida CARD da o'tkazilayotgan va transfer uchun kommisiya miqdoridagi mablag' yetarli ekanligi tekshirish
        if (card.getBalance()>=outcomeDto.getAmount()*0.01){
            Double money=card.getBalance()-outcomeDto.getAmount();
            card.setBalance(money);
            cardRepository.save(card);
            String email = userRepository.findById(userId.getCurrentAuditor().get()).get().getEmail();
            Outcome outcome=new Outcome();
            outcome.setAmount(outcome.getAmount());
            outcome.setCommissionAmount(outcomeDto.getAmount()*0.01);
            outcome.setFromCardId(outcome.getFromCardId());
            outcome.setToCardId(outcome.getToCardId());
            //yuborilgan mablag'lar bazaga saqlandi
            outcomeRepository.save(outcome);
            return new ApiResponse("Mablag'ni qabul qilish uchun emailga habar linki Yuborildi",true,sendEmail(email));

        }
            return new ApiResponse("Mablag' Yetarli emas",false);
        }
        return new ApiResponse("Card No Active",false);
    }
    //READ
    public ApiResponse get(){
        return new ApiResponse("object ",true,outcomeRepository.getAllByAmountOut(userId.getCurrentAuditor().get()));
    }
    //DELETE
    public ApiResponse delete( UUID id ){
        outcomeRepository.deleteById(id);
        return new ApiResponse("object deleted",true);
    }

    public Boolean sendEmail(String sendingEmail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("Test@pdp.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Akkountni Tasdiqlash");
            mailMessage.setText("\"http://localhost:8080/api/auth/api/income?from=\\\"\"\n" +
                    "                    + outcomeDto.getFromCardId() + \"\\\"&to=\\\"\" + outcomeDto.getToCardId() + \"\\\"&amount=\\\"\"+outcomeDto.getAmount()+\"\\\"'>Bablag'ni qabul qilish uchun link</a>\"");
            javaMailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
