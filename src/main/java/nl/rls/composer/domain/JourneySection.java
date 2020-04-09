package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.TrainActivityType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author berend.wilkens
 * <p>
 * Defines the make up of a train for each section of its journey.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class JourneySection extends OwnedEntity {
    /**
     * Origin of the section on which train composition is unchanged
     */
    @ManyToOne
    private Location journeySectionOrigin;
    /**
     * Destination of the section on which train composition is unchanged
     */
    @ManyToOne
    private Location journeySectionDestination;
    /**
     * This element identifies the responsible RU or IM for the actual path section
     */
    @ManyToOne
    private Responsibility responsibilityActualSection;
    /**
     * This element identifies the responsible RU and IM for the following path
     * section
     */
    @ManyToOne
    private Responsibility responsibilityNextSection;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private TrainComposition trainComposition;
    @OneToMany(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "journey_section_id")
    private List<TrainActivityType> activities = new ArrayList<>();
    @ManyToOne
    private Train train;

    public JourneySection(Integer ownerId) {
        super(ownerId);
    }

    public TrainActivityType getActivityById(Integer id) {
        for (TrainActivityType ait : activities) {
            if (ait.getId() == id) {
                return ait;
            }
        }
        return null;
    }

    public void addActivity(TrainActivityType entity) {
        if (!activities.contains(entity)) {
            activities.add(entity);
        }
    }

    public void removeActivityById(int id) {
        TrainActivityType entity = getActivityById(id);
        if (entity != null) {
            removeActivity(entity);
        }
    }

    public void removeActivity(TrainActivityType entity) {
        activities.remove(entity);
    }
}
