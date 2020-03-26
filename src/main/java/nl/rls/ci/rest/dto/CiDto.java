package nl.rls.ci.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.IdentifiableRepresentationModel;

import java.util.Date;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class CiDto extends IdentifiableRepresentationModel<CiDto> {
    private Date createDate;
    private Date postDate;
    private boolean posted = false;
    private UicHeaderDto uicHeader;
    private UicRequestDto uicRequest;
    private UicResponseDto uicResponse;
}

