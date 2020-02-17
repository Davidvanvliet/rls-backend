package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author berend.wilkens
 * Code list
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class LocationSubsidiaryCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * LocationSubsidiaryTypeCode
     */
    private String value;
    private String description;
    private String extra;
}
