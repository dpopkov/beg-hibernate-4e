package learn.beghibernate.lifecycle;

import javax.persistence.PrePersist;

public class UserAccountListener {
    @PrePersist
    void setPasswordHash(Object obj) {
        UserAccount ua = (UserAccount) obj;
        if (ua.getSalt() == null || ua.getSalt() == 0) {
            ua.setSalt((int) (Math.random() * 65535));
        }
        ua.setPasswordHash(ua.getPassword().hashCode() * ua.getSalt());
    }
}
