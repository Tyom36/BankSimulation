package rogue.tyom.sub.securityapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
@NoArgsConstructor
public class TransferDto {
    private String fromAccount;
    private String toAccount;
    private BigDecimal value;

    public TransferDto(String fromAccount, String toAccount, BigDecimal value) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.value = value;
    }
}
