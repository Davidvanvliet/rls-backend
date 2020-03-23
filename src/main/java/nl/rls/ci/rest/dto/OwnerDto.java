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
public class OwnerDto extends RepresentationModel {
    private String code;
    private String name;

}
