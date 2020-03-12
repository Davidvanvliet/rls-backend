package nl.rls.ci.aa.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto extends RepresentationModel {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
