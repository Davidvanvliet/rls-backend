package nl.rls.ci.aa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class LicenseDto extends RepresentationModel {
    private Date validFrom;
    private Date validTo;
    private String contract;

}
