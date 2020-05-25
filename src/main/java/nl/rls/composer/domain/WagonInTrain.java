package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.domain.code.BrakeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WagonInTrain extends OwnedEntity {
    /**
     * Identifies the position of a wagon within a train.
     * Sequential number starting with the first wagon at the front of train as N°1.
     */
    private int position;
    /**
     * Actual wagon parameters, dependent on load or damage. This group and its elements are optional (contract defines what IM requires). But if there is dangerous goods in the train, then this group is mandatory.
     */
    private BrakeType brakeType;
    private int totalLoadWeight;
    //	@ManyToOne
//	private ExceptionalGaugingProfile exceptionalGaugingProfile;
//	@ManyToOne
//	private ExceptionalGaugingIdent exceptionalGaugingIdent;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "wagon_in_train_id")
    private List<DangerGoodsInWagon> dangerGoodsInWagons = new ArrayList<DangerGoodsInWagon>();
    //	@OneToMany
//	private List<InfoOnGoodsShapeTypeDanger> infoOnGoodsShapeTypeDanger;
//	@ManyToMany
//	private List<RestrictionCode> restrictions;
    @ManyToOne
    private Wagon wagon;
    @ManyToOne
    private TrainComposition trainComposition;

    public DangerGoodsInWagon getDangerGoodsInWagonById(Integer dangerGoodsInWagonId) {
        for (DangerGoodsInWagon diw : dangerGoodsInWagons) {
            if (diw.getId() == dangerGoodsInWagonId) {
                return diw;
            }
        }
        return null;
    }

    public void addDangerGoodsInWagon(DangerGoodsInWagon dangerGoodsInWagon) {
        dangerGoodsInWagons.add(dangerGoodsInWagon);

    }

    public void removeDangerGoodsById(int dangerGoodsInWagonId) {
        DangerGoodsInWagon dangerGoodsInWagon = getDangerGoodsInWagonById(dangerGoodsInWagonId);
        if (dangerGoodsInWagon != null) {
            dangerGoodsInWagons.remove(dangerGoodsInWagon);
        }

    }

    public Integer getWeight() {
        int weight = 0;
        weight += totalLoadWeight;
        if (wagon != null) {
            weight += wagon.getWagonWeightEmpty();
        }
        return weight;
    }

    public Integer getNumberOfAxles() {
        if (wagon != null) {
            return wagon.getWagonNumberOfAxles();
        }
        return 0;
    }

    public Integer getMaxSpeed() {
        if (wagon != null) {
            return wagon.getMaxSpeed();
        }
        return Integer.MAX_VALUE;
    }


}
