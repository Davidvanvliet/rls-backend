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
 * <p>
 * Indicates the wagon loading gauge. When the wagon loading gauge is
 * marked on the wagon the information must be provided in the RSRD
 * message. Codes are defined in UIC leaflet 505-1/503 and EN
 * 15273-2:2013 Code list.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class NormalLoadingGauge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String value;
    private String description;
}
