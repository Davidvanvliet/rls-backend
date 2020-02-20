package nl.rls.ci.aa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class License {
    @Transient
    public static String LICENSE_FREE = "LICENSE_FREE";
    @Transient
    public static String LICENSE_ADMIN = "LICENSE_ADMIN";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date validFrom;
    private Date validTo;
    private String contract;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public License(String contract, Date validFrom, Date validTo, Owner owner) {
        this.contract = contract;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "License [id=" + id + ", validFrom=" + validFrom + ", validTo=" + validTo + ", contract=" + contract
                + "]";
    }
}
