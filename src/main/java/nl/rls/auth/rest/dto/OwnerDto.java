package nl.rls.auth.rest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OwnerDto {
    private String name;
    private String companyCode;
    private int id;
    private int userCount;
}
