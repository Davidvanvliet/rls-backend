package nl.rls.ci.domain;

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
