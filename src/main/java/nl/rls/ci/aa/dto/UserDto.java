package nl.rls.ci.aa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto extends ResourceSupport {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}