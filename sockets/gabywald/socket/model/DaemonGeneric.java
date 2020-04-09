package gabywald.socket.model;

import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Gabriel Chandesris (2013, 2015-2016, 2020)
 */
public abstract class DaemonGeneric<T extends DaemonGenericClientThread> implements Runnable {
	
	protected boolean running;
	
	private List<DaemonGenericClientThread> tabClient;
	protected int port;
	protected String out;
	
	protected ServerSocket server;
	
	public DaemonGeneric(int atta) {
		this.running	= true;
		this.port		= atta;
		this.out		= new String("");
		
		try {
			this.server				= new ServerSocket();
			SocketAddress sockAddr	= new InetSocketAddress(this.port);
			this.server.bind(sockAddr);
			this.out = "Serveur opérationnel sur le port: "+this.port;
			this.tabClient = new ArrayList<DaemonGenericClientThread>();
			Logger.printlnLog(LoggerLevel.LL_DEBUG, this.out);
		} catch (IOException e) {
			// e.printStackTrace();
			this.out = new String("Transmission error (daemon:constructor) !");
			Logger.printlnLog(LoggerLevel.LL_WARNING, this.out);
		}
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "Création Daemon [" + "" + ":" + this.port + "]");
	}
	
	public void start() {
		Thread thrThis = new Thread(this);
		thrThis.start();
	}
	
	protected void addClient(DaemonGenericClientThread client) {
		this.tabClient.add(client);
	
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "Connection from [" + client.getHost() + ":" + client.getPort() + "]");
		
	}
	
	public void remClient(DaemonGenericClientThread client) {
		this.tabClient.remove(client);
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "Close connection [" + client.getHost() + ":" + client.getPort() + "]");
	}
	
//	private DaemonClientThread getLastClient() {
//		if (this.tabClient.length > 0) 
//			{ return this.tabClient[this.tabClient.length-1]; } 
//		else  { return null; }
//	}
	
	public String getOutput() { return this.out; }
	
}
