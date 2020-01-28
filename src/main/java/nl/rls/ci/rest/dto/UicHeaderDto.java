package nl.rls.ci.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@ToString
@NoArgsConstructor
@Getter @Setter
public class UicHeaderDto extends ResourceSupport {
	private String messageIdentifier;
	private String messageLiHost;
	private boolean compressed = false;
	private boolean encrypted = false;
	private boolean signed = false;

}
