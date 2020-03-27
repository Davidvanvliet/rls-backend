package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author berend.wilkens
 * Identification code of a freight wagon based on the TSI OPE and CEN Recommendations and CIS wagons coded according to OSJD-UIC leaflet 402, which allows the conversion from 8 digits to 12 digits and viceversa.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Wagon extends OwnedEntity {
    private String numberFreight;
    private String typeName;
    private String code;
    /**
     * Length over buffers is expressed in cm.
     */
    private int lengthOverBuffers;
    /**
     * Number of Axels for a wagon
     */
    private int wagonNumberOfAxles;
    /**
     * Coding in 404-2, chapter 1.7
     */
    private int handBrakeBrakedWeight;
    private int wagonWeightEmpty;


    // private List<Integer> technicalRestrictions;
    /**
     * Indicates the wagon loading gauge. When the wagon loading gauge is marked on
     * the wagon the information must be provided in the RSRD message. Codes are
     * defined in UIC leaflet 505-1/503 and EN 15273-2:2013 Code list.
     */
    //@ManyToOne
    //private NormalLoadingGauge normalLoadingGauge;
    /**
     * Classification of air brake. additional code: 8 No air brake or brake pipe
     * The code is defined in UIC Leaflet 920-13.
     */
    //private AirBrakeType airBrakeType;
    //private int brakingPowerVariationDevice;
    //private int airBrakeSpecialCharacteristic;
    /**
     * Classification of hand brake:
     *
     * 0 No hand brake 1 Ground-operated hand brake 2 Platform-operated hand brake
     */
    //private HandBrakeType handBrakeType;
}
