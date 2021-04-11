package uz.pdp.appjwtrealemailauditing.payload;

import lombok.Data;
import java.util.Date;
@Data
public class CardDto {

    private Long number;

    private Date expiredDate;

}
