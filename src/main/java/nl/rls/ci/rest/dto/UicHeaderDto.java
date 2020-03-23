package nl.rls.ci.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class UicHeaderDto  extends RepresentationModel {
    private String messageIdentifier;
    private String messageLiHost;
    private boolean compressed = false;
    private boolean encrypted = false;
    private boolean signed = false;

}
