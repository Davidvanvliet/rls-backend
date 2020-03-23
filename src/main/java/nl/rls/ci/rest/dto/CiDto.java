package nl.rls.ci.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class CiDto extends RepresentationModel {
    private Date createDate;
    private Date postDate;
    private boolean posted = false;
    private UicHeaderDto uicHeader;
    private UicRequestDto uicRequest;
    private UicResponseDto uicResponse;
}

