package nl.rls.composer.rest.dto;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.WagonOperationalData;

@XmlRootElement
@NoArgsConstructor
@Getter @Setter
public class WagonDto extends ResourceSupport {
    private int wagonTrainPosition;
	private WagonIdentDto wagonIdent;
    private WagonOperationalData wagonOperationalData;

}
