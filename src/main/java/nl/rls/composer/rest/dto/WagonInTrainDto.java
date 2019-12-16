package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class WagonInTrainDto extends ResourceSupport {
    private int position;
    private WagonLoadDto wagonLoad;
}
