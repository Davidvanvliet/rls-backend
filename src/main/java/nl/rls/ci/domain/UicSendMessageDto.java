package nl.rls.ci.domain;

public class UicSendMessageDto {
    private UICMessageDto uicMessageDto;
    private String messageIdentifier;
    private String messageLiHost;
    private boolean compressed;
    private boolean encrypted;
    private boolean signed;

    public UicSendMessageDto() {
    }

    public UICMessageDto getUicMessageDto() {
        return uicMessageDto;
    }

    public void setUicMessageDto(UICMessageDto uicMessageDto) {
        this.uicMessageDto = uicMessageDto;
    }

    public String getMessageIdentifier() {
        return messageIdentifier;
    }

    public void setMessageIdentifier(String messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }

    public String getMessageLiHost() {
        return messageLiHost;
    }

    public void setMessageLiHost(String messageLiHost) {
        this.messageLiHost = messageLiHost;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }
}
