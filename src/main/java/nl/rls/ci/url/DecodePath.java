package nl.rls.ci.url;

public class DecodePath {
	public static Integer decodeInteger(String urlString, String resource) {
		URL url = new URL(urlString);
		return new Integer(url.getChildDirectory(resource));
	}

	public static String decodeString(String urlString, String resource) {
		URL url = new URL(urlString);
		return url.getChildDirectory(resource);
	}

}
