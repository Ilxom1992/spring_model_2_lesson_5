package uz.pdp.appjwtrealemailauditing.payload;

import lombok.Data;
import uz.pdp.appjwtrealemailauditing.entity.Card;

import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;
@Data
public class IncomeDto {
    private Long fromCardId;
    private Long toCardId;
    private Double amount;
    private UUID cardId;
}
