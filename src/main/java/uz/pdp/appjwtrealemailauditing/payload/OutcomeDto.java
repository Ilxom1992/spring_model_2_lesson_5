package uz.pdp.appjwtrealemailauditing.payload;

import lombok.Data;

import java.util.Date;
import java.util.UUID;
@Data
public class OutcomeDto {
    private Long fromCardId;
    private Long toCardId;
    private Double amount;
    private Date date;
    private Double commissionAmount;
    private UUID cardId;
}
