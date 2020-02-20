package nl.rls.ci.url;

public class DecodePath {
    public static Integer decodeInteger(String url, String resource) {
        URL localUrl = new URL(url);
        return new Integer(localUrl.getChildDirectory(resource));
    }

    public static String decodeString(String url, String resource) {
        URL localUrl = new URL(url);
        return localUrl.getChildDirectory(resource);
    }

}
