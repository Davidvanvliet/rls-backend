package nl.rls.composer.rest.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rls.composer.domain.code.DangerLabel;
import nl.rls.composer.rest.dto.hateoas.ResourceSupport;

/**
 * @author berend.wilkens
 * This element indicates the type of a dangerous load
 */
@NoArgsConstructor
@Getter @Setter
public class DangerGoodsTypeDto extends ResourceSupport {
    private String hazardIdentificationNumber;
    /**
     * The UNNumber of the dangerous good according to the RID chapter 3.2, table A, column 1. Mandatory, except it concerns a declaration of an empty packaging of the type "EMPTY PACKAGING", "EMPTY RECEPTACLE &lt;=1000L", "EMPTY IBC" or "EMPTY LARGE PACKAGING" .
     */
    private String unNumber;
    private List<DangerLabel> dangerLabel;
    /**
     * The Class of the dangerous good according to the RID chapter 3.2, table A, column 3a.
     */
    private String ridClass;
    private String packingGroup;
   /**
     * Indicator for labelled dangerous goods in limited quantity according to chapter 3.-4 RID
     */
    private boolean limitedQuantityIndicator;
}
