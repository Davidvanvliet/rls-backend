package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Location {
    @Id
    private int locationPrimaryCode;
    private String countryCodeIso;
    private String code;
    /**
     * Location Name in an official language of the Country using the ISO Unicode alphabet
     */
    private String primaryLocationName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "responsible_code",
            referencedColumnName = "code"
    )
    private Company responsible;
    private String latitude;
    private String longitude;
    private int active_flag;
}