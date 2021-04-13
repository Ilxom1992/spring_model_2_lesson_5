package uz.pdp.appjwtrealemailauditing.payload;

import lombok.Data;
import java.util.Date;


@Data
public class CardDto {

    private Date expiredDate;
    private Long number;


}
