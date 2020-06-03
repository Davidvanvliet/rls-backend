package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.TractionMode;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TractionInTrain other = (TractionInTrain) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public Integer getWeight() {
        if (traction != null) {
            return traction.getWeight();
        }
        return 0;
    }

    public Integer getNumberOfAxles() {
        if (traction != null) {
            return traction.getNumberOfAxles();
        }
        return 0;
    }

}
