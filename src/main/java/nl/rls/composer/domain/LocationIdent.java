package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@NoArgsConstructor
@Getter @Setter
public class LocationIdent {
	@Id 	
    private int locationPrimaryCode;
    private String countryIso;
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