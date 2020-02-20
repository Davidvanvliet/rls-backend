package nl.rls.composer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author berend.wilkens
 * <p>
 * Used for unique identification of the objects handled in the messages such as train,
 * path, path request or case reference.
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class CompositIdentifierOperationalType extends OwnedEntity {
    /**
     * Provides a possibility for differentiation between the objects:
     * Train, Path, Case Reference and Path Request
     */
    private String objectType;
    @ManyToOne
    private Company company;
    /**
     * It is the main part of identifier and is determent by
     * the company that creates  it.
     */
    private String core;
    /**
     * The variant shows a relationship between two identifiers
     * referring to the same business case
     */
    private String variant;
    /**
     * Refers to the timetable period in which the business will be carried out
     */
    private int timetableYear;
    /**
     * The start of the date/time in effect
     */
    private Date startDate;
}
