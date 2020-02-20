package nl.rls.composer.domain.code;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author berend.wilkens
 * <p>
 * Identifies the type of a locomotive: First digit:
 * “0” = not specified
 * “1” = external electric power supply for traction (catenary and pantograph, third rail or other such as maglev)
 * “2” = on-board traction power supply for traction without external electrical or other power supply available
 * “3” = hybrid traction (both on-board or electric traction available)
 * Second digit (definitions in chapter 2.2.2 of the LOC and PAS TSI 1302/2014):
 * “0” = not specified
 * “1” = locomotive or power unit
 * “2” = trainset or multiple unit or railcar
 * “3” = shunter
 * “4” = on track machine or infrastructure inspection vehicle
 */
@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
public class TractionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private String description;

}
