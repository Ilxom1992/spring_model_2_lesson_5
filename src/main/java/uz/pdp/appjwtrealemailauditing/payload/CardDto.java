package uz.pdp.appjwtrealemailauditing.payload;

import lombok.Data;
import java.util.Date;
import java.util.UUID;

@Data
public class CardDto {

    private Date expiredDate;
    private boolean isActive;
    private Long number;
    private String username;
    private UUID userId;

}
