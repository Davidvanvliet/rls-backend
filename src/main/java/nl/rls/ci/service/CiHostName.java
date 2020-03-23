package nl.rls.ci.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class CiHostName {
    public static String getPublicHostName() {
        String systemipaddress = "";
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            InputStream inputStream = url_name.openStream();
            System.out.println("Stream: " + inputStream);
            System.out.println("Stream: " + inputStream.toString());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("BufferedReader: " + bufferedReader.toString());
            // reads system IPAddress
            System.out.println("BufferedReader: " + bufferedReader.lines());
            System.out.println("BufferedReader: " + bufferedReader.lines().toString());
            systemipaddress = bufferedReader.readLine().trim();
        } catch (Exception e) {
            e.printStackTrace();
            systemipaddress = "IP-address not in DNS ";
        }
        return systemipaddress;
    }

    public static String getLocalHostName() {
        InetAddress localhost;
        try {
            localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress().trim();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "Cannot Execute Properly, IP-address not in DNS";
        }
    }

}
