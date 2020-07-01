package nl.rls.composer.domain.message;

/**
 * @author berend.wilkens
 * Assigned by the Sender 1=creation, 2=modification, 3=deletion
 */
public enum MessageStatus {
    creation(1),
    modification(2),
    deletion(3);

    private int value;

    MessageStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
