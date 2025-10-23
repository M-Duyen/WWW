package iuh.fit.ongk_bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account {
    private long accountNumber;
    private String ownerName;
    private String cardNumber;
    private String ownerAddress;
    private double amount;
}
