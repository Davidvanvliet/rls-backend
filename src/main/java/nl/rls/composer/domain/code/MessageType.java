package nl.rls.composer.domain.code;

public enum MessageType {
	TRAIN_COMPOSITION_MESSAGE (3003);
	
	private int code;
	private String VERSION = "2.1.6";
	
	MessageType(int code) {
		this.code = code;
	}
	
	public int code() {
		return code;
	}
	
	public String version() {
		return VERSION;
	}

}
