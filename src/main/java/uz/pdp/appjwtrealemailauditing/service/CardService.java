package uz.pdp.appjwtrealemailauditing.service;

import org.springframework.stereotype.Service;
import uz.pdp.appjwtrealemailauditing.config.KimYozganiniBilish;
import uz.pdp.appjwtrealemailauditing.entity.Card;
import uz.pdp.appjwtrealemailauditing.entity.User;
import uz.pdp.appjwtrealemailauditing.payload.ApiResponse;
import uz.pdp.appjwtrealemailauditing.payload.CardDto;
import uz.pdp.appjwtrealemailauditing.repository.CardRepository;
import uz.pdp.appjwtrealemailauditing.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CardService {

final CardRepository cardRepository;
final UserRepository userRepository;
    public CardService( CardRepository cardRepository, UserRepository userRepository) {

        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }
    KimYozganiniBilish kimYozganiniBilish=new KimYozganiniBilish();
    //  CREATE
    public ApiResponse addCard(CardDto cardDto){
        Card card=new Card();
        boolean exists = cardRepository.existsByNumber(cardDto.getNumber());
        if (exists){
            return new ApiResponse("Bunday Carta Bazada mavjud",false);
        }

        //sestemadagi userni olish
        Optional<User> optionalUser = userRepository.findById(kimYozganiniBilish.getCurrentAuditor().get());
        card.setUser(optionalUser.get());
        card.setNumber(cardDto.getNumber());
        card.setExpiredDate(cardDto.getExpiredDate());
        // CARTANINIG ACTIVLIGI  QO'LDA KIRITILDI HOZIRCHA
        card.setActive(true);

        cardRepository.save(card);
        return new ApiResponse("object saved",true);
    }
    //READ
    public ApiResponse getCard(){
        return new ApiResponse("Your Cards ",true,
                cardRepository.findAllByUserId(kimYozganiniBilish.getCurrentAuditor().get()));
    }
    //DELETE
    public ApiResponse deleteCard( UUID id ){
        cardRepository.deleteById(id);
        return new ApiResponse("Card deleted",true);
    }

}
