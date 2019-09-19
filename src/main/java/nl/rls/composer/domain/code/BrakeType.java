package nl.rls.composer.domain.code;

/**
 * @author berend.wilkens 
 * Type of braking system. additional: X For indication:
 *         brake system of the freight wagon out of order (actually / current)
 * 
 */
public enum BrakeType {
	G ("G"),
	P ("P"),
	X ("X");
	
	private String code;
	
	BrakeType (String code) {
		this.code = code;
	}
	
	public String code() {
		return code;
	}
	
}
