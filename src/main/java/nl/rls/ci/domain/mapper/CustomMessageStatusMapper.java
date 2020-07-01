package nl.rls.ci.domain.mapper;

import nl.rls.ci.domain.LiTechnicalAck;
import nl.rls.composer.domain.CustomMessageStatus;

public class CustomMessageStatusMapper {
    public static CustomMessageStatus map(LiTechnicalAck liTechnicalAck) {
        boolean acknowledged = false;
        if (liTechnicalAck.getAckIndentifier().equals("ACK")) {
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
