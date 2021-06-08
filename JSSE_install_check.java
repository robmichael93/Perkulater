/*
 * JSSE_install_check.java
 *
 * Created on June 6, 2001, 2:01 PM
 */


/**
 *
 * @author  Rob Michael
 * @version 
 */
import java.net.*;
import javax.net.ssl.*;

public class JSSE_install_check {

    public static void main(String[] args) throws Exception {

	SSLServerSocketFactory factory =
	    (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();

	SSLServerSocket sslSocket =
	    (SSLServerSocket)factory.createServerSocket(5757);

	String [] cipherSuites = sslSocket.getEnabledCipherSuites();

	for (int i = 0; i < cipherSuites.length; i++) {
	    System.out.println("Cipher Suite " + i +
		" = " + cipherSuites[i]);
	}
    }
}

