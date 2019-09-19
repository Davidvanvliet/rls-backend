package nl.rls.composer.rest.dto;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter @Setter
public class CompositIdentifierOperationalTypeDto extends ResourceSupport {
    private String objectType;
    private CompanyDto company;
    private String core;
    private String variant;
    private int timetableYear;
    private Date startDate;
}
