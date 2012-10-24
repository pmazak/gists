import java.net.*;
import java.util.*;

    Authenticator authenticator = new Authenticator() {    
    public PasswordAuthentication getPasswordAuthentication() {
            return (new PasswordAuthentication("username",
                    "password".toCharArray()));
        }
    };
    Authenticator.setDefault(authenticator);
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy-host.com", 8080));
    URL url = new URL(urlString);
    System.out.println("connecting with proxy to: " + urlString);
    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection(proxy);