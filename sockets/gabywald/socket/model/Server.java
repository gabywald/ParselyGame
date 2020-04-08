package gabywald.socket.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * 
 * @author Gabriel Chandesris (2013)
 * @deprecated useful ?
 */
public class Server {
	
	public static void main(String[] args) {
		int port	= Integer.valueOf(args[0]);
		try {
			ServerSocket server		= new ServerSocket();
			SocketAddress sockAddr	= new InetSocketAddress(port);
			server.bind(sockAddr);
			System.out.println("Serveur opérationnel sur le port: "+port);
			String line				= null;
			do {
				Socket client		= server.accept();
				String clientAddr	= client.getInetAddress().getHostAddress();
				System.out.println("Connexion à partir de: "+clientAddr);
				InputStream cis		= client.getInputStream();
				OutputStream cos	= client.getOutputStream();
				byte[] readBuffer	= null;
				do {
					while (cis.available() == 0) { ; }
					readBuffer		= new byte[cis.available()];
					cis.read(readBuffer);
					line			= new String(readBuffer);
					System.out.println(client+" a envoyé : "+line);
					String reply	= "Bien reçu : "+line;
					cos.write(reply.getBytes());
					cos.flush();
				} while ( (!"quit".equalsIgnoreCase(line)) 
							&& (!"shutdown".equalsIgnoreCase(line)) );
				if ("quit".equalsIgnoreCase(line)) {
					cos.close();
					System.out.println("Connexion fermée: "+client);
				}
			} while (!"shutdown".equals(line));
			server.close();
			System.out.println("Serveur arrêté.");
		} catch (IOException e) { e.printStackTrace(); }
	}
}
