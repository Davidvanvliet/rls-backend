package nl.rls.ci.aa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OwnerDto extends RepresentationModel {
    private String code;
    private String name;
    private List<LicenseDto> licenses;

}
