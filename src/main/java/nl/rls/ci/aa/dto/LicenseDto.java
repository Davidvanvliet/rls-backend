package nl.rls.ci.aa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class LicenseDto extends IdentifiableRepresentationModel<LicenseDto> {
    private Date validFrom;
    private Date validTo;
    private String contract;

}
