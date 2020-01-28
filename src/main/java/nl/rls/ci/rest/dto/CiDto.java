package nl.rls.ci.rest.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@ToString
@NoArgsConstructor
@Getter @Setter
public class CiDto extends ResourceSupport {
    private Date createDate;
    private Date postDate;
    private boolean posted = false;
    private UicHeaderDto uicHeader;
    private UicRequestDto uicRequest;
    private UicResponseDto uicResponse;
}

