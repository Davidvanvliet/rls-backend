package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.TractionType;

/**
 * @author berend.wilkens Defines the actual Type, the number and the mode of
 * deployment of a traction unit of the freight train.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Traction extends OwnedEntity {
    @ManyToOne
    protected TractionType tractionType;
    /**
     * Composite identifier for the loco types and locomotives. First four
     * elements identify the series of the loco, rest can identify the exact
     * individual locomotive.
     */
    protected String locoTypeNumber;
    /**
     * Identifies the number of the locomotive, usually the European Vehicle Number
     * on 12N. It is currently not restricted only to numeric values.
     */
    protected String locoNumber;
    private String typeName;
    /**
     * used for displaying icons in the front-end
     */
    private String code;
    private int lengthOverBuffers;
    private int numberOfAxles;
    private int weight;
    private int brakeWeightG;
    private int brakeWeightP;
    public Traction(Integer ownerId) {
        super(ownerId);
    }

}
