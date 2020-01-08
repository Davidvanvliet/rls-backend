package nl.rls.composer.rest.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "All Danger Label of dangerous good according to the RID chapter 3.2, table A, column 5, excepting the shunting labels Model 13 and 15 (CODE: OTIF RID-Specification).")
public class DangerLabelDto extends ResourceSupport {
	private String code;
	private String description;
}
