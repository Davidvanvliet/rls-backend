package nl.rls.ci.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LiTechnicalAckDto {
    private String responseStatus;
    private MessageReferenceDto MessageReference;
    private String RemoteLIName;
}
