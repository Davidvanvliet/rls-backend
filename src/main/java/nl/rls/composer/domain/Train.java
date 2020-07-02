package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.ci.domain.CustomMessageStatus;
import nl.rls.composer.domain.message.TrainCompositionMessage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ToString
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Train extends OwnedEntity {
    /**
     * 1 Passenger train Commercial train with passenger coaches or trainsets Empty
     * run of Train with passenger coaches or trainsets Including Crew train (for
     * Train Crew Members)
     * 2 Freight train Train with freight wagons
     * 3 Light engine (locomotive train) One or more engines without any carriages
     * 4 Engineering train Train for measurement, maintenance, instructions, homologation, etc
     * 0 Other Train types that are not covered with the four codes given above can be
     * codified as "other" in the messages Passenger with Freight - military trains,
     * the Overnight Express; Royalty, Head of States
     */
    private int trainType;
    /**
     * Identifies the train for traffic management purposes by the Dispatcher, GSMR services, etc.
     */
    private String operationalTrainNumber;
    /**
     * The scheduled departure date and time or the scheduled handover date and time at the border between two different IMs.
     */
    private Date scheduledTimeAtHandover;
    /**
     * Scheduled Date and Time at a location related to the status of the train or wagon at the given location.
     */
    private Date scheduledDateTimeAtTransfer;
    /**
     * Transfer point or station of destination in the considered network
     */
    @ManyToOne
    private Location transferPoint;
    /**
     * Next IM
     */
    @ManyToOne
    private Company transfereeIM;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "train_id")
    private List<JourneySection> journeySections = new ArrayList<JourneySection>();

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<CustomMessageStatus> customMessageStatuses = new ArrayList<>();

    @Transient
    private List<TrainCompositionMessage> trainCompositionMessages = new ArrayList<TrainCompositionMessage>();

    @Transient
    public JourneySection getJourneySectionById(int id) {
        for (JourneySection trainCompositionJourneySection : journeySections) {
            if (trainCompositionJourneySection.getId() == id) {
                return trainCompositionJourneySection;
            }
        }
        return null;
    }

    public void addJourneySection(JourneySection journeySection) {
        journeySections.add(journeySection);
//        comment.setPost(this);
    }

    public void removeJourneySection(JourneySection journeySection) {
        journeySections.remove(journeySection);
//        comment.setPost(null);
    }

}
