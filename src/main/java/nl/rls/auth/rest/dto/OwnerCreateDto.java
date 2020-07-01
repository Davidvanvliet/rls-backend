package nl.rls.auth.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class OwnerCreateDto {
    private String companyCode;
    private List<String> auth0Ids;
}
