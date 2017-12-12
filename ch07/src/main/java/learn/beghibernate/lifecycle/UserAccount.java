package learn.beghibernate.lifecycle;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@EntityListeners(UserAccountListener.class)
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Transient
    private String password;
    private Integer salt;
    private Integer passwordHash;

    public boolean validPassword(String newPass) {
        if (salt == null || passwordHash == null) return false;
        return newPass.hashCode() * salt == getPasswordHash();
    }
}
