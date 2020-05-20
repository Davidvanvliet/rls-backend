package nl.rls.composer.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.TractionMode;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TractionInTrain {
    /**
     * 0 - no driver present in Loco, 1 - driver(s) is /are) present in Loco
     */
    protected Integer driverIndication;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Identifies position of intermediate traction unit(s) in the train indicating
     * after which wagon (specified by order number) the unit is placed.
     */
    private Integer position;
    /**
     * Identifies the mode of deployment of a traction within a train First digit –
     * traction role Second digit – position in group of traction units with the
     * same role
     */
    @ManyToOne
    protected TractionMode tractionMode;

    @ManyToOne
    private Traction traction;
    @ManyToOne
    private TrainComposition trainComposition;

}
