package uz.pdp.appjwtrealemailauditing.service;

import org.springframework.stereotype.Service;
import uz.pdp.appjwtrealemailauditing.config.KimYozganiniBilish;
import uz.pdp.appjwtrealemailauditing.entity.Card;
import uz.pdp.appjwtrealemailauditing.entity.Income;
import uz.pdp.appjwtrealemailauditing.payload.ApiResponse;
import uz.pdp.appjwtrealemailauditing.payload.IncomeDto;
import uz.pdp.appjwtrealemailauditing.repository.CardRepository;
import uz.pdp.appjwtrealemailauditing.repository.IncomeRepository;

import java.util.UUID;

@Service
public class IncomeService {
final CardRepository cardRepository;
final IncomeRepository incomeRepository;

    public IncomeService(CardRepository cardRepository, IncomeRepository incomeRepository) {
        this.cardRepository = cardRepository;
        this.incomeRepository = incomeRepository;
    }
KimYozganiniBilish user=new KimYozganiniBilish();

    //  INCOME MONEY
    public ApiResponse incomeMoney(IncomeDto incomeDto){
        Card card = cardRepository.findByNumber(incomeDto.getToCardId());
        if (card!=null){
           //bazadagi mablag'ga kelgan mablag'ni qo'shish
            Double money=card.getBalance()+incomeDto.getAmount();
            //mablag' cartaga qo'shildi
            card.setBalance(money);
            //kelgan mablag' bazaga yozildi
            Income income=new Income();
            income.setAmount(incomeDto.getAmount());
            income.setToCardId(incomeDto.getToCardId());
            income.setFromCardId(income.getFromCardId());
            incomeRepository.save(income);
            return new ApiResponse("Money Saved",true);
        }
        return new ApiResponse("Card not found",false);
    }
    //READ
    public ApiResponse getIncomeMoney(){
        return new ApiResponse("object ",true,incomeRepository.getAllByAmountIn(user.getCurrentAuditor().get()));
    }
    //DELETE
    public ApiResponse delete( UUID id ){
        incomeRepository.deleteById(id);
        return new ApiResponse("object deleted",true);
    }

}
