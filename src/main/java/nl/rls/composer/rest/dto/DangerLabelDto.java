package nl.rls.composer.rest.dto;

import org.springframework.hateoas.ResourceSupport;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "All Danger Label of this dangerous good according to the RID chapter 3.2, table A, column 5, excepting the shunting labels Model 13 and 15 (CODE: OTIF RID-Specification).")
public class DangerLabelDto extends ResourceSupport {
	private String value;
	private String description;
}
