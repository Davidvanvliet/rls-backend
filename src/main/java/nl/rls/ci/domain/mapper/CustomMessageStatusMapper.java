package nl.rls.ci.domain.mapper;

import nl.rls.ci.domain.CustomMessageStatus;
import nl.rls.ci.domain.LiTechnicalAck;

public class CustomMessageStatusMapper {
    public static CustomMessageStatus map(LiTechnicalAck liTechnicalAck) {
        boolean acknowledged = false;
        if (liTechnicalAck.getResponseStatus().equals("ACK")) {
            acknowledged = true;
        }
        return new CustomMessageStatus(
                acknowledged,
                liTechnicalAck.getSender(),
                liTechnicalAck.getRemoteLIName(),
                liTechnicalAck.getMessageReference().getMessageDateTime().toGregorianCalendar().getTime(),
                liTechnicalAck.getMessageReference().getMessageIdentifier()
        );
    }


}
