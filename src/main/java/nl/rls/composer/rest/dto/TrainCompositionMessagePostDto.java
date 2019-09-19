package nl.rls.composer.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TrainCompositionMessagePostDto {
    private String messageIdentifier;
    private String senderReference;
	private String transferPoint;
}
