package nl.rls.ci.aa.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LicenseDto {
	private Date validFrom;
	private Date validTo;
	private String contract;

}
