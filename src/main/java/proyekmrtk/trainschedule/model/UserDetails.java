package proyekmrtk.trainschedule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDetails {
    private String username;
    private double balance;

    public UserDetails(String username,
                       double balance) {
        this.username = username;
        this.balance = balance;
    }
}
