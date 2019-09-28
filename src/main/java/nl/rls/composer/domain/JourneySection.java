package nl.rls.composer.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author berend.wilkens
 * Defines the data provided by the IM for a journey section
 */
@Entity
@NoArgsConstructor
@Getter 
@Setter
public class JourneySection extends OwnedEntity {
	public JourneySection(Integer ownerId) {
		super(ownerId);
	}
	/**
	 * Origin of the section on which train composition is unchanged
	 */
	@ManyToOne
    private LocationIdent journeySectionOrigin;
	/**
	 * Destination of the section on which train composition is unchanged
	 */
	@ManyToOne
    private LocationIdent journeySectionDestination;
	/**
	 * This element identifies the responsible RU or IM for the actual path section
	 */
	@ManyToOne
    private Responsibility responsibilityActualSection;
	/**
	 * This element identifies the responsible RU and IM for the following path section
	 */
	@ManyToOne
    private Responsibility responsibilityNextSection;
}
