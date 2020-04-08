package gabywald.socket.model;

import gabywald.global.json.JSONObject;
import gabywald.utilities.logger.Logger;
import gabywald.utilities.logger.Logger.LoggerLevel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;

/**
 * 
 * @author Gabriel Chandesris (2013, 2015)
 */
public class DaemonClientThread extends Observable implements Runnable {
	private Daemon mot;
	private Socket soc;
	private String inn;
	private JSONObject out;
	// private boolean show;

	public DaemonClientThread(Socket s, Daemon mother)	{
		this.mot	= mother;
		this.soc	= s;
		this.inn	= new String("");
		this.out	= new JSONObject();
		// this.show	= true;
		this.addObserver(new DaemonClientThreadObserver());
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "Création DaemonClientThread [" + this.soc + ":" + this.mot + "]");
	}
	
	private void changeAndNotify(JSONObject output) {
		this.out = output;
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t output.toString() --" + output.toString() + "--");
		this.setChanged();
		this.notifyObservers();
	}
	
	private void changeAndNotify(String output) {
		JSONObject tmpJSONobj = new JSONObject();
		tmpJSONobj.put("chgmsgdaem", output);
		this.changeAndNotify(tmpJSONobj);
	}
	
//	private void changeAndNotifyError(String output) {
//		this.out.put("error", output);
//		this.changeAndNotify(this.out);
//	}
	
	public void start() {
		Thread thrThis = new Thread(this);
		thrThis.start();
	}
	
	public void stop() {
		this.inn = "exit";
	}
	
	public void run() {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "DaemonClientThread.run(); BEGIN");
		try {
			String clientAddr	= this.soc.getInetAddress().getHostAddress();
			this.changeAndNotify("Connexion a partir de: "+clientAddr);
			InputStream cis		= this.soc.getInputStream();
			OutputStream cos	= this.soc.getOutputStream();
			byte[] readBuffer	= null;
			do {
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t data awaiting...");
//				while (cis.available() == 0) 
//					{ ; } // { Logger.printlnLog(LL_DEBUG, "\t cis.available(): (" + cis.available() + ")"); }
//				readBuffer		= new byte[cis.available()];
//				cis.read(readBuffer);
//				this.inn		= new String(readBuffer);
				
				// Alternate awaiting data...
				byte[] b1		= new byte[1];
				int nbRead		= cis.read(b1);
				
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t nbRead: (" + nbRead + ")");
				
				if (nbRead <= 0) 
					{ Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t break !");break; }
				int available	= cis.available();
				
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t available: (" + available + ")");

				readBuffer		= new byte[available + 1];
				readBuffer[0]	= b1[0];
				if (available > 0) {
					cis.read(readBuffer, 1, available);
				}
				this.inn		= new String(readBuffer);
				
				// ***** ***** ***** ***** ***** 
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "this.inn: {" + this.inn + "}");
				
				this.changeAndNotify(this.soc+" a envoyé : ["+this.inn+"]");
				
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t init treatment...");
				JSONObject toSendToClient = this.treatment();
				Logger.printlnLog(LoggerLevel.LL_DEBUG, "\t end treatment...");
				// toSendToClient.put("returning", "Bien reçu : ["+this.inn+"]");
				cos.write(toSendToClient.toJSON().getBytes());
				cos.flush();
			} while ( (!"exit".equalsIgnoreCase(this.inn)) 
							&& (!"shutdown".equalsIgnoreCase(this.inn)) );
			if ("exit".equalsIgnoreCase(this.inn)) {
				cos.close();
				this.changeAndNotify("Connexion fermée: "+this.soc);
				this.mot.remClient(this);
			} // END "if ("quit".equalsIgnoreCase(this.inn))"s
			if ("shutdown".equalsIgnoreCase(this.inn)) {
				this.mot.remClient(this);
				this.changeAndNotify("Serveur arrêté. ");
				System.exit(0);
			} // END "if ("shutdown".equalsIgnoreCase(this.inn))"
		} catch (IOException e) { e.printStackTrace(); }
		
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "DaemonClientThread.run(); END");
	}
	
	public String getOutput() { 
		this.out.put("src", "["+this.getPort()+"]");
		return this.out.toJSON(); 
	}
	
	public String getHost()		
		{ return this.soc.getInetAddress().getHostAddress(); }
	
	public String getPort()		
		{ return this.soc.getPort()+""; }
	
	/**
	 * This method HAS TO be overrided by inheritant classes !
	 */
	public JSONObject treatment() {
		Logger.printlnLog(LoggerLevel.LL_DEBUG, "DaemonClientThread.treatment()...");
		String toReturn				= "... treament ...";
		JSONObject outputJSONobj	= new JSONObject();
		// TODO in inheritant !
		outputJSONobj.put("toclient", toReturn);
		return outputJSONobj;
	}
	
	protected String getInput() 
		{ return this.inn; }
	
	protected Daemon getParent() 
		{ return this.mot; }
}
