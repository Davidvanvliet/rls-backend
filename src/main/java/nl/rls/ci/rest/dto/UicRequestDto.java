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
public class UicRequestDto extends RepresentationModel {
    private String signature;
    private String senderAlias;
    private String encoding;
}
