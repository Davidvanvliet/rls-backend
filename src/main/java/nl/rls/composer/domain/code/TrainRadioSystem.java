package nl.rls.composer.domain.code;

/**
 * @author berend.wilkens
 * The on board radio system of the train in coded format
 */
public enum TrainRadioSystem {
	ONE("1"),
	TWO("2");
	
	private String type;
	
	TrainRadioSystem(String type) {
		this.type = type;
	}
	
	public String type() {
		return type;
	}

}
