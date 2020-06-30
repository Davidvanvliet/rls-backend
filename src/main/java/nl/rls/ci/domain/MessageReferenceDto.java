package nl.rls.ci.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.datatype.XMLGregorianCalendar;

@NoArgsConstructor
@Getter
@Setter
public class MessageReferenceDto {
    protected String messageIdentifier;
    protected XMLGregorianCalendar messageDateTime;
}
