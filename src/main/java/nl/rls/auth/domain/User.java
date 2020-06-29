package nl.rls.auth.domain;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String userId;

    @ManyToOne
    private Owner owner;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
